package com.movierecomander.backend.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.movierecomander.backend.movies.movie.MovieSearchService.movieNameContains;

@RestController
@RequestMapping(path = "api/movies")
public class MovieSearchController {
    private final MovieSearchService movieSearchService;

    @Autowired
    MovieSearchController(MovieSearchService movieSearchService){
        this.movieSearchService = movieSearchService;
    }

    @GetMapping()
    public List<Movie> searchMovieByTag(String movieName){
        return movieSearchService.getMovieByTag(movieName);
    }
}
