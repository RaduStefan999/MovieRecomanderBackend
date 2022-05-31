package com.movierecommender.backend.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.users.user.AppUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Review implements Serializable
{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "movieId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Movie ID is mandatory!")
    Movie movie;

    @Min(1)
    @Max(5)
    private Integer reviewValue;

    public Review() {
    }

    public Review(ReviewDTO reviewDTO)
    {
        this.appUser = reviewDTO.getAppUser();
        this.movie = reviewDTO.getMovie();
        this.reviewValue = reviewDTO.getReviewValue();
    }

    public Review(AppUser appUser, Movie movie, Integer reviewValue) {
        this.appUser = appUser;
        this.movie = movie;
        this.reviewValue = reviewValue;
    }

    public Review(Long id, AppUser appUser, Movie movie, Integer reviewValue) {
        this.id = id;
        this.appUser = appUser;
        this.movie = movie;
        this.reviewValue = reviewValue;
    }

    public void update(Review review) {
        this.appUser = review.appUser;
        this.movie = review.movie;
        this.reviewValue = review.reviewValue;
    }

    public void update(ReviewDTO reviewDTO) {
        this.appUser = reviewDTO.getAppUser();
        this.movie = reviewDTO.getMovie();
        this.reviewValue = reviewDTO.getReviewValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getReviewValue() {
        return reviewValue;
    }

    public void setReviewValue(Integer reviewValue) {
        this.reviewValue = reviewValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id.equals(review.id) && appUser.equals(review.appUser) && movie.equals(review.movie) && reviewValue.equals(review.reviewValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appUser, movie, reviewValue);
    }
}
