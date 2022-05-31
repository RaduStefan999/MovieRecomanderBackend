package com.movierecommender.backend.movies.moviegenre;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class MovieGenre implements Serializable
{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String genre;

    public MovieGenre() {}
    public MovieGenre(String genre) {
        this.genre = genre;
    }

    public MovieGenre(MovieGenreDTO movieGenreDTO)
    {
        this.id = movieGenreDTO.getId();
        this.genre = movieGenreDTO.getGenre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieGenre that = (MovieGenre) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MovieGenre{" +
                "id=" + id +
                ", genre='" + genre + '\'' +
                '}';
    }
}
