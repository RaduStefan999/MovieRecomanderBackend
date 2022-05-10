package com.movierecommender.backend.movies.profiler;

import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieListService {
    private final MovieRepository movieRepository;

    public MovieListService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie getMovieById(Long id) {
        return movieRepository.getById(id);
    }

    public Long getMovieId(Movie movie) {
        return movie.getId();
    }
}
