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
        Optional<List<Long>> movieIds = this.getMlRecommendation(userId, nrOfMovies)
                .timeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()))
                .onErrorReturn(Optional.empty()).block();

        if (movieIds != null && movieIds.isPresent()) {

            List<Movie> recommendedMovies = movieIds.get().stream().filter
                    (id -> movieRepository.existsById(id)).map(id -> movieRepository.getById(id))
                    .toList();

            if (movieRepository.count() != 0) {
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
                .uri("/ML/{userId}/{nrOfMovies}", userId, nrOfMovies)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Optional<List<Long>>>() {});
    }

    private List<Movie> getFallbackRecommendation(long userId, int nrOfMovies) {
        return movieRepository.findAll().stream().limit(nrOfMovies).toList();
    }
}
