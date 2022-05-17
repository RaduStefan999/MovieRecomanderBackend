package com.movierecomander.backend.movies.moviesearch;

import com.movierecomander.backend.movies.movie.Movie;
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
<<<<<<< Updated upstream:movieRecomander/backend/src/main/java/com/movierecomander/backend/movies/moviesearch/MovieSearchController.java
    public ResponseEntity<List<Movie>> searchMovieByGenres(List<String> movieGenres){
=======
    public ResponseEntity<List<Movie>> searchMovieByGenres(@PathVariable List<String> movieGenres){
>>>>>>> Stashed changes:movieRecomander/backend/src/main/java/com/movierecommender/backend/movies/moviesearch/MovieSearchController.java
        return ResponseEntity.ok(movieSearchService.getMovieByGenres(movieGenres));
    }
}
