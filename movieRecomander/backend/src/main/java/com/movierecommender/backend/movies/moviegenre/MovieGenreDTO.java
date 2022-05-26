package com.movierecommender.backend.movies.moviegenre;


import java.util.Objects;

public class MovieGenreDTO
{
	private Long id;
	private String genre;

	public MovieGenreDTO()
	{
	}

	public MovieGenreDTO(String genre)
	{
		this.genre = genre;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MovieGenreDTO that = (MovieGenreDTO) o;
		return Objects.equals(id, that.id) && Objects.equals(genre, that.genre);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id, genre);
	}
}
