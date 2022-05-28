package com.movierecommender.backend.security.auth;

import com.movierecommender.backend.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DbUserAuthDao implements UserAuthDao {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public DbUserAuthDao(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Optional<UserAuth> selectUserAuthByEmail(String email) {
        return userService.getUserByEmail(email).stream().findFirst().map(user -> new UserAuth(user));
    }
}
