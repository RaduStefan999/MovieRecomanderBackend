package com.movierecommender.backend.movies.moviegenre;

<<<<<<< HEAD

=======
>>>>>>> ae25cf14d26c0c574973f948231a9d34edd4aaee
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/genres")
public class MovieGenreController {

    MovieGenreRepository movieGenreRepository;

    @Autowired
    public MovieGenreController(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<MovieGenre>> get(){
        return ResponseEntity.ok(movieGenreRepository.findAll());
    }

}
