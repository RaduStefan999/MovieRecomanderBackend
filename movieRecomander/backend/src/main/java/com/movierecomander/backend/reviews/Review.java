package com.movierecomander.backend.reviews;

import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.users.user.AppUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "movieId")
    Movie movie;

    private Integer reviewValue;

    public Review() {
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
