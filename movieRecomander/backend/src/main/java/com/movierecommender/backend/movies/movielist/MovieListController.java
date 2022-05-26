package com.movierecommender.backend.movies.movielist;

import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/movie/profiler")
public class MovieListController {
    @Autowired
    MovieRepository movieRepository;
    private final SecureRandom random = new SecureRandom();

    @GetMapping("/{movieList}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Movie>>  movieListPref(){
        int count=0;

        List <Movie> movies=new ArrayList<>();
        if(movieRepository.findAll().size()>5) {
            while (count < 5) {
                int randomNextMovie = random.nextInt(movieRepository.findAll().size());
                   if(!movies.contains(movieRepository.findAll().get(randomNextMovie))) {
                       movies.add(movieRepository.findAll().get(randomNextMovie));
                       count++;
                   }
            }
        }
        else
            if(!movieRepository.findAll().isEmpty()) {
                movies.addAll(movieRepository.findAll());
            }
            else
                return (ResponseEntity<List<Movie>>) ResponseEntity.notFound();
        return ResponseEntity.ok(movies);
    }
}
