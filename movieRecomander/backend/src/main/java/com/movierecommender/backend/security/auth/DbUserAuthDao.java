package com.movierecommender.backend.security.auth;

import com.movierecommender.backend.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DbUserAuthDao implements UserAuthDao {
    private final UserService userService;

    @Autowired
    public DbUserAuthDao(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserAuth> selectUserAuthByEmail(String email) {
        return userService.getUserByEmail(email).stream().findFirst().map(UserAuth::new);
    }
}
