package com.movierecommender.backend.mlrecommender;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.movies.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/recommendation")
public class MLRecommenderController {
    private final MLRecommenderService mlRecommenderService;
    private final IdentityService identityService;

    @Autowired
    public MLRecommenderController(MLRecommenderService mlRecommenderService, IdentityService identityService) {
        this.mlRecommenderService = mlRecommenderService;
        this.identityService = identityService;
    }

    @GetMapping("/{nrOfMovies}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Movie>> getRecommendation(@PathVariable int nrOfMovies) {
        var currentUser = identityService.getLoggedInUser();

        if (currentUser.isEmpty()) {
            throw new BusinessException("Could not get current user", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(mlRecommenderService.getRecommendation(currentUser.get().getId(), nrOfMovies));
    }

}
