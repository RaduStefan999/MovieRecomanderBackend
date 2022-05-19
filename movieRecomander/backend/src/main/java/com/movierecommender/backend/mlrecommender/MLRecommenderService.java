package com.movierecommender.backend.mlrecommender;

import com.movierecommender.backend.BackendConfig;
import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.movies.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class MLRecommenderService {

    private final BackendConfig backendConfig;
    private final MLRecommenderConfig mlRecommenderConfig;

    @Autowired
    public MLRecommenderService(MLRecommenderConfig mlRecommenderConfig, BackendConfig backendConfig) {
        this.backendConfig = backendConfig;
        this.mlRecommenderConfig = mlRecommenderConfig;
    }

    public List<Movie> getRecommendation(long userId, int nrOfMovies) {
        Optional<List<Long>> movieIds = this.getMlRecommendation(userId, nrOfMovies)
                .timeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()))
                .onErrorReturn(Optional.empty()).block();

        Optional<List<Movie>> movies = this.selectMovies(nrOfMovies, movieIds == null ? Optional.empty() : movieIds)
                .timeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just(Optional.empty());
                }).block();

        if (movies == null || movies.isEmpty()) {
            throw new BusinessException("Failed to make request to own api", "Invalid self request",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return movies.get();
    }

    private Mono<Optional<List<Long>>> getMlRecommendation(long userId, int nrOfMovies) {
        WebClient mlMicroservice = WebClient.create(mlRecommenderConfig.getMlURI());

        return mlMicroservice.get()
            .uri(mlRecommenderConfig.getMlURI() + "/prediction?user_id=" + userId + "&movies_nr=" + nrOfMovies)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Optional<List<Long>>>() {});
    }

    private Mono<Optional<List<Movie>>> selectMovies(int nrOfMovies, Optional<List<Long>> movieIds) {
        WebClient selfService = WebClient.create(backendConfig.getSelfURI());

        return selfService.post()
            .uri("/api/v1/movies/selector/" + nrOfMovies)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(Mono.just(movieIds), new ParameterizedTypeReference<Optional<List<Long>>>() {})
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<Optional<List<Movie>>>() {});
    }
}
