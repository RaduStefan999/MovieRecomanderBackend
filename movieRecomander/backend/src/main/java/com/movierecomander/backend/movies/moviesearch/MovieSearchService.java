package com.movierecomander.backend.movies.moviesearch;


import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.movies.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieSearchService {

    private final MovieRepository movieRepository;


    @Autowired
    public MovieSearchService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    static Specification<Movie> movieNameContains(String name) {
        return (movie, cq, cb) -> cb.like(movie.get("name"), "%" + name + "%");
    }

    public List<Movie> getMovieByTag(String movieName) {
        return movieRepository.findAll(movieNameContains(movieName));
    }
}
