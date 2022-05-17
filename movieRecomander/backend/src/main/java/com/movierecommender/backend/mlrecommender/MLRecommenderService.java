package com.movierecommender.backend.mlrecommender;

import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class MLRecommenderService {

    MLRecommenderConfig mlRecommenderConfig;
    MovieRepository movieRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public MLRecommenderService(MLRecommenderConfig mlRecommenderConfig, MovieRepository movieRepository) {
        this.mlRecommenderConfig = mlRecommenderConfig;
        this.movieRepository = movieRepository;
    }

    public List<Movie> getRecommendation(long userId, int nrOfMovies) {
        Session session = entityManager.unwrap(Session.class);

        Optional<List<Long>> movieIds = this.getMlRecommendation(userId, nrOfMovies);

        var x = (Movie) entityManager.createQuery("FROM Movie m WHERE m.id = :id")
                .setParameter("id", 1L)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getSingleResult();

        if (movieIds != null && movieIds.isPresent()) {

            List<Movie> recommendedMovies = movieIds.get().stream().filter
                    (id -> movieRepository.existsById(id)).map(id -> movieRepository.getById(id))
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

    private Optional<List<Long>> getMlRecommendation(long userId, int nrOfMovies) {
        System.out.println(mlRecommenderConfig.getMlURI() + "/prediction?user_id=" + userId + "&movies_nr=" + nrOfMovies);
        return this.getRestTemplateBuilder().build().exchange(
                mlRecommenderConfig.getMlURI() + "/prediction?user_id=" + userId + "&movies_nr=" + nrOfMovies,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Optional<List<Long>>>() {}).getBody();
    }

    private RestTemplateBuilder getRestTemplateBuilder() {
        return new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()))
            .setReadTimeout(Duration.ofSeconds(mlRecommenderConfig.getMlTimeout()));
    }

    private List<Movie> getFallbackRecommendation(long userId, int nrOfMovies) {
        return movieRepository.findAll().stream().limit(nrOfMovies).toList();
    }
}
