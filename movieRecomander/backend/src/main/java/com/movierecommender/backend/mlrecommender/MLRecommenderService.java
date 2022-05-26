package com.movierecommender.backend.mlrecommender;

import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class MLRecommenderService {

    MLRecommenderConfig mlRecommenderConfig;
    MovieRepository movieRepository;

    @Autowired
    public MLRecommenderService(MLRecommenderConfig mlRecommenderConfig, MovieRepository movieRepository) {
        this.mlRecommenderConfig = mlRecommenderConfig;
        this.movieRepository = movieRepository;
    }

    public List<Movie> getRecommendation(long userId, int nrOfMovies) {
        var allMovies = movieRepository.findAll();

        List<Long> movieIds = this.getMlRecommendation(userId, nrOfMovies)
                .timeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()))
                .onErrorReturn(Collections.emptyList()).block();

        if (movieIds != null && !movieIds.isEmpty()) {

            var recommendedMovies = allMovies.stream().filter(movie -> movieIds.contains(movie.getId()))
                                                .toList();

            if (recommendedMovies.isEmpty())
                return recommendedMovies;
        }

        return getFallbackRecommendation(nrOfMovies);
    }

    private Mono<List<Long>> getMlRecommendation(long userId, int nrOfMovies) {
        WebClient mlMicroservice = WebClient.create(mlRecommenderConfig.getMlURI());

        return mlMicroservice.get()
                .uri(mlRecommenderConfig.getMlURI() + "/prediction?user_id=" + userId + "&movies_nr=" + nrOfMovies)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>(){});
    }

    private List<Movie> getFallbackRecommendation(int nrOfMovies) {
        return movieRepository.findAll().stream().limit(nrOfMovies).toList();
    }
}
