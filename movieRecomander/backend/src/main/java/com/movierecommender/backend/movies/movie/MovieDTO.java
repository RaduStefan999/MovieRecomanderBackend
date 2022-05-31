package com.movierecommender.backend.movies.movie;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import com.movierecommender.backend.reviews.Review;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MovieDTO
{
	private Long id;
	private String name;
	private String summary;
	private String description;
	private Integer ageRestriction;

	private List<MovieGenre> movieGenres;
	private String releaseDate;
	private Integer duration;
	private String trailerLink;
	private String movieLink;
	private String thumbnailLink;

	Set<Review> ratings;
	Set<Comment> comments;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Integer getAgeRestriction()
	{
		return ageRestriction;
	}

	public void setAgeRestriction(Integer ageRestriction)
	{
		this.ageRestriction = ageRestriction;
	}

	public List<MovieGenre> getMovieGenres()
	{
		return movieGenres;
	}

	public void setMovieGenres(List<MovieGenre> movieGenres)
	{
		this.movieGenres = movieGenres;
	}

	public String getReleaseDate()
	{
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate)
	{
		this.releaseDate = releaseDate;
	}

	public Integer getDuration()
	{
		return duration;
	}

	public void setDuration(Integer duration)
	{
		this.duration = duration;
	}

	public String getTrailerLink()
	{
		return trailerLink;
	}

	public void setTrailerLink(String trailerLink)
	{
		this.trailerLink = trailerLink;
	}

	public String getMovieLink()
	{
		return movieLink;
	}

	public void setMovieLink(String movieLink)
	{
		this.movieLink = movieLink;
	}

	public String getThumbnailLink()
	{
		return thumbnailLink;
	}

	public void setThumbnailLink(String thumbnailLink)
	{
		this.thumbnailLink = thumbnailLink;
	}

	public Set<Review> getRatings()
	{
		return ratings;
	}

	public void setRatings(Set<Review> ratings)
	{
		this.ratings = ratings;
	}

	public Set<Comment> getComments()
	{
		return comments;
	}

	public void setComments(Set<Comment> comments)
	{
		this.comments = comments;
	}

	public void validate() {
		try {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate.parse(this.releaseDate, dateFormatter);
		} catch (DateTimeParseException e) {
			throw new BusinessException("Invalid date or invalid format.", "Json Format", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MovieDTO movieDTO = (MovieDTO) o;
		return Objects.equals(id, movieDTO.id) && Objects.equals(name, movieDTO.name) && Objects.equals(summary, movieDTO.summary) && Objects.equals(description, movieDTO.description) && Objects.equals(ageRestriction, movieDTO.ageRestriction) && Objects.equals(movieGenres, movieDTO.movieGenres) && Objects.equals(releaseDate, movieDTO.releaseDate) && Objects.equals(duration, movieDTO.duration) && Objects.equals(trailerLink, movieDTO.trailerLink) && Objects.equals(movieLink, movieDTO.movieLink) && Objects.equals(thumbnailLink, movieDTO.thumbnailLink) && Objects.equals(ratings, movieDTO.ratings) && Objects.equals(comments, movieDTO.comments);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, name, summary, description, ageRestriction, movieGenres, releaseDate, duration, trailerLink, movieLink, thumbnailLink, ratings, comments);
	}
}
