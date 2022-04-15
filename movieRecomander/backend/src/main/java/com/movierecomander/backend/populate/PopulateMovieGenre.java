package com.movierecomander.backend.populate;

import com.movierecomander.backend.movies.moviegenre.MovieGenre;
import com.movierecomander.backend.movies.moviegenre.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PopulateMovieGenre implements ApplicationRunner {

    private final MovieGenreRepository movieGenreRepository;

    @Autowired
    public PopulateMovieGenre(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    public void run(ApplicationArguments args) {

        if (Arrays.stream(args.getSourceArgs()).anyMatch(argument -> argument.equals("populate")))
        {
            String[] genres = {"Drama", "Comedy", "Action", "Romantic", "Scary", "Sci-Fi", "Fantasy"};
            List<MovieGenre> movieGenres = Arrays.stream(genres).map(genre -> new MovieGenre(genre)).collect(Collectors.toList());

            movieGenreRepository.saveAll(movieGenres);
        }
    }
}
