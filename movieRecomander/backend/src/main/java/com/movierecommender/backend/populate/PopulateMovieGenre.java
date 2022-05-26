package com.movierecommender.backend.populate;

import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import com.movierecommender.backend.movies.moviegenre.MovieGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PopulateMovieGenre implements ApplicationRunner {

    private final MovieGenreRepository movieGenreRepository;

    @Autowired
    public PopulateMovieGenre(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    public void run(ApplicationArguments args) {

        if (Arrays.asList(args.getSourceArgs()).contains("populate"))
        {
            String[] genres = {"Drama", "Comedy", "Action", "Romantic", "Scary", "Sci-Fi", "Fantasy"};
            List<MovieGenre> movieGenres = Arrays.stream(genres).map(MovieGenre::new).toList();

            movieGenreRepository.saveAll(movieGenres);
        }
    }
}
