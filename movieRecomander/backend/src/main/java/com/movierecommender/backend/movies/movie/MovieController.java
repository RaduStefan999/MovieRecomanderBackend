package com.movierecommender.backend.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movie/base")
public class MovieController {
    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<Movie> get(){
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Movie> getByID(@PathVariable("id") Long id){
        if(movieRepository.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(movieRepository.findById(id).get());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody Movie movie){
        movieRepository.save(movie);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie){
        var foundMovie = movieRepository.findById(id);
        if(foundMovie.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        foundMovie.get().update(movie);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteMovie(@PathVariable("id") Long id){
        movieRepository.deleteById(id);
    }
}
