package com.movierecomander.backend.movies.moviesearch;

import com.movierecomander.backend.movies.movie.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movie")
public class MovieSearchController {
    private final MovieSearchService movieSearchService;

    @Autowired
    MovieSearchController(MovieSearchService movieSearchService){
        this.movieSearchService = movieSearchService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public List<Movie> searchMovieByTag(String movieName){
        return movieSearchService.getMovieByTag(movieName);
    }
}
