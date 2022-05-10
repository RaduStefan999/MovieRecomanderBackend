package com.movierecommender.backend.security.auth;

import java.util.Optional;

public interface UserAuthDao {
    Optional<UserAuth> selectUserAuthByEmail(String email);
}
