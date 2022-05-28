package com.movierecomander.backend.movies.moviesearch;

import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.movies.moviegenre.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movies/search")
public class MovieSearchController {
    private final MovieSearchService movieSearchService;

    @Autowired
    MovieSearchController(MovieSearchService movieSearchService){
        this.movieSearchService = movieSearchService;
    }

    @GetMapping("/name/{movieName}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Movie>> getMovieByName(@PathVariable String movieName){
        return ResponseEntity.ok(movieSearchService.getMovieByName(movieName));
    }

    @GetMapping("/genre/{movieGenres}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Movie>> searchMovieByGenres(@PathVariable List<MovieGenre> movieGenres){
        return ResponseEntity.ok(movieSearchService.getMovieByGenres(movieGenres));
    }
}
