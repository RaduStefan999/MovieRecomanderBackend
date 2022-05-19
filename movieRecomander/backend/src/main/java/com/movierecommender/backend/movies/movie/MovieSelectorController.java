package com.movierecommender.backend.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies/selector")
public class MovieSelectorController {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieSelectorController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @PostMapping("/{nr}")
    public List<Movie> getMovies(@PathVariable("nr") int nr, @RequestBody Optional<List<Long>> movieIds){

        var allMovies = movieRepository.findAll().stream().toList();

        if (movieIds.isPresent()) {

            var recommendedMovies = allMovies.stream().filter(m -> movieIds.get().contains(m.getId())).toList();

            if (recommendedMovies.size() != 0) {
                return recommendedMovies;
            }

            System.out.println("No valid movie ids provided -- fallback");
            return getFallbackRecommendation(nr);
        }

        System.out.println("No movie ids provided -- fallback");
        return getFallbackRecommendation(nr);
    }

    private List<Movie> getFallbackRecommendation(int nrOfMovies) {
        return movieRepository.findAll().stream().limit(nrOfMovies).toList();
    }

}
