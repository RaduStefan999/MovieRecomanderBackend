package com.movierecommender.backend.movies.moviesearch;


import com.movierecommender.backend.movies.movie.Movie;
import com.movierecommender.backend.movies.movie.MovieRepository;
import com.movierecommender.backend.movies.moviegenre.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieSearchService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private final MovieRepository movieRepository;

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
        List<Movie> movies = movieRepository.findAll();
        List<Movie> returnedMovies = new ArrayList<>();

        for(Movie movie : movies) {
            for (MovieGenre genre : movieGenresList)
                if(movie.getMovieGenres().contains(genre))
                {
                    //System.out.println(movie);
                    returnedMovies.add(movie);
                    break;
                }
        }

        return returnedMovies;

        /**CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
         CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Movie.class);
         Root<Movie> root = criteriaQuery.from(Movie.class);*/
        //return em.createQuery(criteriaQuery.select(root).where(root.get("movieGenres").in(movieGenresList))).getResultList();


        //return null;
    }

}
