package com.movierecomander.backend.reviews;

import com.movierecomander.backend.movies.Movie;
import com.movierecomander.backend.users.User;
import com.movierecomander.backend.users.user.AppUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Review {

    @EmbeddedId
    ReviewKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    AppUser appUser;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movieId")
    Movie movie;

    private Integer reviewValue;

    public Review() {
    }

    public Review(ReviewKey id, AppUser appUser, Movie movie, Integer reviewValue) {
        this.id = id;
        this.appUser = appUser;
        this.movie = movie;
        this.reviewValue = reviewValue;
    }

    public ReviewKey getId() {
        return id;
    }

    public void setId(ReviewKey id) {
        this.id = id;
    }

    public User getUser() {
        return appUser;
    }

    public void setUser(User user) {
        this.user = user;
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
        return id.equals(review.id) && user.equals(review.user) && movie.equals(review.movie) && reviewValue.equals(review.reviewValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, movie, reviewValue);
    }
}
