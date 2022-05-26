package com.movierecommender.backend.comments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.users.user.AppUser;

import java.time.LocalDate;
import java.util.Objects;

public class CommentDTO
{
	private Long id;
	private String text;
	private LocalDate commentDate;

	@JsonIgnore
	AppUser appUser;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	Movie movie;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public LocalDate getCommentDate()
	{
		return commentDate;
	}

	public void setCommentDate(LocalDate commentDate)
	{
		this.commentDate = commentDate;
	}

	public AppUser getAppUser()
	{
		return appUser;
	}

	public void setAppUser(AppUser appUser)
	{
		this.appUser = appUser;
	}

	public Movie getMovie()
	{
		return movie;
	}

	public void setMovie(Movie movie)
	{
		this.movie = movie;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CommentDTO that = (CommentDTO) o;
		return Objects.equals(id, that.id) && Objects.equals(text, that.text) && Objects.equals(commentDate, that.commentDate) && Objects.equals(appUser, that.appUser) && Objects.equals(movie, that.movie);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, text, commentDate, appUser, movie);
	}
}
