package com.movierecommender.backend.movies.moviegenre;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/genres")
public class MovieGenreController {
    @Autowired
    private final MovieGenreRepository movieGenreRepository;

    public MovieGenreController(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<MovieGenre>> getAllGenres(){
        return ResponseEntity.ok(movieGenreRepository.findAll());
    }
}
