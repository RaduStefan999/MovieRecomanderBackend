package com.movierecommender.backend.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.users.user.AppUser;

import java.util.Objects;

public class ReviewDTO
{
	Long id;

	@JsonIgnore
	AppUser appUser;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	Movie movie;

	private Integer reviewValue;

	public ReviewDTO()
	{
	}

	public ReviewDTO(AppUser appUser, Movie movie, Integer reviewValue)
	{
		this.appUser = appUser;
		this.movie = movie;
		this.reviewValue = reviewValue;
	}

	public ReviewDTO(Long id, AppUser appUser, Movie movie, Integer reviewValue)
	{
		this.id = id;
		this.appUser = appUser;
		this.movie = movie;
		this.reviewValue = reviewValue;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public Integer getReviewValue()
	{
		return reviewValue;
	}

	public void setReviewValue(Integer reviewValue)
	{
		this.reviewValue = reviewValue;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ReviewDTO reviewDTO = (ReviewDTO) o;
		return Objects.equals(id, reviewDTO.id) && Objects.equals(appUser, reviewDTO.appUser) && Objects.equals(movie, reviewDTO.movie) && Objects.equals(reviewValue, reviewDTO.reviewValue);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, appUser, movie, reviewValue);
	}
}
