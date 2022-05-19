package com.movierecommender.backend.mlrecommender;

import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Optional<List<Long>> movieIds = this.getMlRecommendation(userId, nrOfMovies)
                .timeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()))
                .onErrorReturn(Optional.empty()).block();

        if (movieIds != null && movieIds.isPresent()) {

            var recommendedMovies = allMovies.stream().filter(movie -> movieIds.get().contains(movie.getId()))
                                                .toList();

            if (recommendedMovies.size() != 0) {
                return recommendedMovies;
            }

            System.out.println("ML microservice provided no movies");
            System.out.println("All hope is not yet lost --- trying backend fallback");
            return getFallbackRecommendation(userId, nrOfMovies);
        }

        System.out.println("ML microservice failed to provide prediction");
        System.out.println("All hope is not yet lost --- trying backend fallback");
        return getFallbackRecommendation(userId, nrOfMovies);
    }

    private Mono<Optional<List<Long>>> getMlRecommendation(long userId, int nrOfMovies) {
        WebClient mlMicroservice = WebClient.create(mlRecommenderConfig.getMlURI());

        return mlMicroservice.get()
                .uri(mlRecommenderConfig.getMlURI() + "/prediction?user_id=" + userId + "&movies_nr=" + nrOfMovies)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Optional<List<Long>>>() {});
    }

    private List<Movie> getFallbackRecommendation(long userId, int nrOfMovies) {
        return movieRepository.findAll().stream().limit(nrOfMovies).toList();
    }
}
