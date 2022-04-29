package com.movierecomander.backend.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.movierecomander.backend.movies.movie.MovieSearchService.movieNameContains;

@RestController
public class MovieSearchController {
    private final MovieSearchService movieSearchService;

    @Autowired
    MovieSearchController(MovieSearchService movieSearchService){
        this.movieSearchService = movieSearchService;
    }


    public List<Movie> searchMovieByName(String movieName){
       return movieSearchService.getMovieByName(movieName);
    }

}
