package com.movierecomander.backend.movies.movie;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class MovieDAO {

    EntityManager em;

    List<Movie> searchMovieByName(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);

        Root<Movie> movie = cq.from(Movie.class);
        Predicate movieNamePredicate = cb.like(movie.get("name"),"%" + name + "%");
        cq.where(movieNamePredicate);

        TypedQuery<Movie> query = em.createQuery(cq);
        return query.getResultList();
    }
}
