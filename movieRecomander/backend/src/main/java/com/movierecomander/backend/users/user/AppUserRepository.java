package com.movierecomander.backend.users.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT users FROM AppUser users WHERE users.email = ?1")
    Optional<AppUser> findAppUserByEmail(String email);
}
