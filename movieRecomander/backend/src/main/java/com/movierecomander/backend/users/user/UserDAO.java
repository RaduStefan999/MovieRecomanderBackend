package com.movierecomander.backend.users.user;


import com.movierecomander.backend.users.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAO {
    EntityManager em;

    List<User> searchUserByName(String name){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> user = cq.from(User.class);
        Predicate userNamePredicate = cb.like(user.get("name"),"%" + name + "%");
        cq.where(userNamePredicate);

        TypedQuery<User> query = em.createQuery(cq);
        return query.getResultList();
    }
}
