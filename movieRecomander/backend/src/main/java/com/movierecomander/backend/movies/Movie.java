package com.movierecomander.backend.movies;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "movieId")
public class Movie {
    private String name;
    private String summary;
    private String description;
    private Integer ageRestriction;
    private List<MovieGenre> movieGenres;
    private LocalDate releaseDate;
    private Duration duration;

    private String trailerLink;
    private String movieLink;
}
