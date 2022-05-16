package com.movierecomander.backend.movies.moviesearch;


import com.movierecomander.backend.movies.movie.Movie;
import com.movierecomander.backend.movies.movie.MovieRepository;
import com.movierecomander.backend.movies.moviegenre.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class MovieSearchService {
    @Autowired
    private final MovieRepository movieRepository;

    @PersistenceContext
    private EntityManager em;


    public MovieSearchService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    static Specification<Movie> movieNameContains(String name) {
        return (movie, cq, cb) -> cb.like(movie.get("name"), "%" + name + "%");
    }

    public List<Movie> getMovieByName(String movieName) {
        return movieRepository.findAll(movieNameContains(movieName));
    }

    private List<MovieGenre> getGenres(List<String> genres){
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(MovieGenre.class);
        Root<MovieGenre> root = criteriaQuery.from(MovieGenre.class);

        return em.createQuery(criteriaQuery.select(root).where(root.get("genre").in(genres))).getResultList();
    }

    public List<Movie> getMovieByGenres(List<String> genres){
        List<MovieGenre> movieGenresList = getGenres(genres);
        System.out.println(movieGenresList.size());
        for(MovieGenre genre : movieGenresList)
            System.out.println(genre);

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Movie.class);
        Root<Movie> root = criteriaQuery.from(Movie.class);

        //return em.createQuery(criteriaQuery.select(root).where(root.get("movieGenres").in(movieGenresList))).getResultList();
        return null;
    }
}
