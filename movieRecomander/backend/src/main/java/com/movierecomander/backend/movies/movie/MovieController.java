package com.movierecomander.backend.movies.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {
    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Movie> get(){
        return this.movieRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Optional<Movie> getByID(@PathVariable("id") Long id){
        return movieRepository.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMovie(@RequestBody Movie movie){
        movieRepository.save(movie);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Boolean> updateDescripiton(@PathVariable("id") Long id, @PathVariable("description") String description){
        var foundMovie = movieRepository.findById(id);
        if(foundMovie.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        foundMovie.get().setDescription(description);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteMovie(@PathVariable("id") Long id){
        movieRepository.deleteById(id);
    }
}
