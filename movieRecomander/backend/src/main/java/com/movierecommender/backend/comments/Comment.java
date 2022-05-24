package com.movierecommender.backend.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.users.user.AppUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String text;
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "movieId")
    @JsonIgnore
    Movie movie;

    public Comment() {
    }

    public Comment(Long id, String text, LocalDate commentDate, AppUser appUser, Movie movie) {
        this.id = id;
        this.text = text;
        this.commentDate = commentDate;
        this.appUser = appUser;
        this.movie = movie;
    }

    public void update(Comment comment) {
        this.text = comment.text;
        this.commentDate = comment.commentDate;
        this.appUser = comment.appUser;
        this.movie = comment.movie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDate commentDate) {
        this.commentDate = commentDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) && text.equals(comment.text) && commentDate.equals(comment.commentDate) && appUser.equals(comment.appUser) && movie.equals(comment.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, commentDate, appUser, movie);
    }
}
