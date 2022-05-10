package com.movierecommender.backend.mlrecommender;

import com.movierecommender.backend.movies.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/recommendation")
public class MLRecommenderController {
    private final MLRecommenderService mlRecommenderService;

    @Autowired
    public MLRecommenderController(MLRecommenderService mlRecommenderService) {
        this.mlRecommenderService = mlRecommenderService;
    }

    @GetMapping("/{userId}/{nrOfMovies}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Movie>> getRecommendation(@PathVariable long userId, @PathVariable int nrOfMovies) {
        return ResponseEntity.ok(mlRecommenderService.getRecommendation(userId, nrOfMovies));
    }

}
