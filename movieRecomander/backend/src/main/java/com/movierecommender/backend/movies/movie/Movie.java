package com.movierecommender.backend.movies.movie;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import com.movierecommender.backend.reviews.Review;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@PrimaryKeyJoinColumn(name = "movieId")
public class Movie {
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")

    @Id
    private Long id;

    @NotBlank(message="Movie name is mandatory")
    private String name;

    @Column(length = 5000)
    @Size(max = 5000, message = "Max length is 5000.")
    private String summary;

    @Column(length = 5000)
    @Size(max = 5000, message = "Max length is 5000.")
    private String description;

    private Integer ageRestriction;

    @ManyToMany
    private List<MovieGenre> movieGenres;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Date must be like \" yyyy-MM-dd \".")
    private String releaseDate;

    private Integer duration;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double averageRatingStars;

    @Column(length = 500)
    @Size(max = 500, message = "Max length is 500.")
    private String trailerLink;

    @Column(length = 500)
    @Size(max = 500, message = "Max length is 500.")
    private String movieLink;

    @Column(length = 500)
    @Size(max = 500, message = "Max length is 500.")
    private String thumbnailLink;

    @OneToMany(mappedBy = "movie")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<Review> ratings;

    @OneToMany(mappedBy = "movie")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Set<Comment> comments;

    public Movie() {
    }

    public Movie(String name, String summary, String description, Integer ageRestriction,
                 List<MovieGenre> movieGenres, String releaseDate, Integer duration, String trailerLink,
                 String movieLink, String thumbnailLink, Set<Review> ratings, Set<Comment> comments) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.ageRestriction = ageRestriction;
        this.movieGenres = movieGenres;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.trailerLink = trailerLink;
        this.movieLink = movieLink;
        this.thumbnailLink = thumbnailLink;
        this.ratings = ratings;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Integer ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public List<MovieGenre> getMovieGenres() {
        return movieGenres;
    }

    public void setMovieGenres(List<MovieGenre> movieGenres) {
        this.movieGenres = movieGenres;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @JsonIgnore
    public LocalDate getInterpretedReleaseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(releaseDate, formatter);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }

    public String getThumbnailLink() {
        return thumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.thumbnailLink = thumbnailLink;
    }

    public Set<Review> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Review> ratings) {
        this.ratings = ratings;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void isValid() {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(this.releaseDate, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Invalid date or invalid format.", "Json Format", HttpStatus.BAD_REQUEST);
        }
    }

    public Double getAverageRatingStars() {
        int totalRatings = this.ratings.stream().map(Review::getReviewValue).
                mapToInt(Integer::intValue).sum();
        int nrOfRatings = this.ratings.size();

        if (nrOfRatings <= 0) {
            return 1.0;
        }

        return (double)totalRatings / nrOfRatings;
    }

    public void update(Movie movie) {
        this.name = movie.name;
        this.summary = movie.summary;
        this.description = movie.description;
        this.ageRestriction = movie.ageRestriction;
        this.movieGenres = movie.movieGenres;
        this.releaseDate = movie.releaseDate;
        this.duration = movie.duration;
        this.trailerLink = movie.trailerLink;
        this.movieLink = movie.movieLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id) && name.equals(movie.name) && Objects.equals(summary, movie.summary) && Objects.equals(description, movie.description) && Objects.equals(movieGenres, movie.movieGenres) && releaseDate.equals(movie.releaseDate) && Objects.equals(duration, movie.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, summary, description, movieGenres, releaseDate, duration);
    }
}
