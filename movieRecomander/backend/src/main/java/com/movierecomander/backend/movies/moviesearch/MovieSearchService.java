package com.movierecomander.backend.movies.moviesearch;


import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.movies.movie.MovieRepository;
import com.movierecomander.backend.movies.moviegenre.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSearchService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private final MovieRepository movieRepository;

    public MovieSearchService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    static Specification<Movie> movieNameContains(String name) {
        return (movie, cq, cb) -> cb.like(movie.get("name"), "%" + name + "%");
    }

    public List<Movie> getMovieByName(String movieName) {
        return movieRepository.findAll(movieNameContains(movieName));
    }



}
