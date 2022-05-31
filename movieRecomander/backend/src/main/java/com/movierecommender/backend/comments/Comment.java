package com.movierecommender.backend.comments;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.users.user.AppUser;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Entity
public class Comment implements Serializable
{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(length = 2000)
    @Size(min = 3, max = 2000, message = "Max length is 2000.")
    private String text;
    
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Date must be like \" yyyy-MM-dd \".")
    private String commentDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "movieId")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Movie ID is mandatory!")
    Movie movie;

    public Comment() {
    }

    public Comment(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.text = commentDTO.getText();
        this.commentDate = commentDTO.getCommentDate();
        this.appUser = commentDTO.getAppUser();
        this.movie = commentDTO.getMovie();
    }

    public void update(CommentDTO commentDTO) {
        this.text = commentDTO.getText();
        this.commentDate = commentDTO.getCommentDate();
        this.appUser = commentDTO.getAppUser();
        this.movie = commentDTO.getMovie();
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

    public String getCommentDate() {
        return commentDate;
    }

    @JsonIgnore
    public LocalDate getInterpretedCommentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(commentDate, formatter);
    }

    public void setCommentDate(String commentDate) {
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
