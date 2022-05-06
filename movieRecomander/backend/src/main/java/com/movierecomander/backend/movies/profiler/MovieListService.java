package com.movierecomander.backend.movies.profiler;


import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.movies.movie.MovieRepository;
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
}
