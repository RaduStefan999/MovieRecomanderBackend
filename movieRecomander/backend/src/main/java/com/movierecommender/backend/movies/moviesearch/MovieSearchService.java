package com.movierecommender.backend.movies.moviesearch;


import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import com.movierecommender.backend.movies.moviegenre.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieSearchService {

    private final MovieGenreRepository movieGenreRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public MovieSearchService(MovieGenreRepository movieGenreRepository, MovieRepository movieRepository){
        this.movieGenreRepository = movieGenreRepository;
        this.movieRepository = movieRepository;
    }

    static Specification<Movie> movieNameContains(String name) {
        return (movie, cq, cb) -> cb.like(movie.get("name"), "%" + name + "%");
    }

    public List<Movie> getMovieByName(String movieName) {
        return movieRepository.findAll(movieNameContains(movieName));
    }

    public List<Movie> getMovieByGenres(List<String> genres){
        List<MovieGenre> movieGenresList = movieGenreRepository.findAll().stream().filter(
                g -> genres.contains(g.getGenre())).toList();
        //este un numar limitat de movie genre in baza de date
        //nu cred ca e un drop in eficienta daca le luam pe toate si le filtram

        return movieRepository.findAll().stream().filter(
                m -> m.getMovieGenres().stream().anyMatch(movieGenresList::contains)
        ).toList();
    }
}
