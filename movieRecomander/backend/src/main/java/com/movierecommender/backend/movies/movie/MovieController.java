package com.movierecommender.backend.movies.movie;

import com.movierecommender.backend.advice.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<Movie>> get(){
        return ResponseEntity.ok(movieRepository.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Movie> getByID(@PathVariable("id") Long id){
        if (movieRepository.findById(id).isEmpty()) {
            throw new BusinessException("Movie not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(movieRepository.findById(id).get());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void addMovie(@RequestBody Movie movie){ movieRepository.save(movie); }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public void updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie){
        var foundMovie = movieRepository.findById(id);
        if(foundMovie.isEmpty()){
            throw new BusinessException("Movie not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        foundMovie.get().update(movie);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void deleteMovie(@PathVariable("id") Long id){
        movieRepository.deleteById(id);
    }
}
