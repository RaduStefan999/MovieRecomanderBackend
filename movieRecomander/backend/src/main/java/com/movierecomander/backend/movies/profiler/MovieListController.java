package com.movierecomander.backend.movies.profiler;

import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.movies.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(path = "api/v1/movie/profiler")
public class MovieListController {
    @Autowired
    MovieRepository movieRepository;
    MovieListService movieListService;

    @GetMapping("/{movieList}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public List  MovieListPref(){
        int count=0;
        Random random=new Random();
        List <Movie> movies=new ArrayList<>();
        while(count<5){
            long randomWithNextLong= random.nextLong();
            if(movieRepository.findById(randomWithNextLong)!= null) {
                movies.add(movieListService.getMovieById(randomWithNextLong));
                count++;
            }
        }
        return movies;
    }
}
