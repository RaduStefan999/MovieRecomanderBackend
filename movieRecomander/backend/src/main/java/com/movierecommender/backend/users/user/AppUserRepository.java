package com.movierecommender.backend.users.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>, JpaSpecificationExecutor<AppUser> {

    @Query("SELECT users FROM AppUser users WHERE users.email = ?1")
    Optional<AppUser> findAppUserByEmail(String email);

    @Query("SELECT users FROM AppUser users WHERE users.id = ?1")
    Optional<AppUser> findAppUserById(@PathVariable("id") Long id);

}
