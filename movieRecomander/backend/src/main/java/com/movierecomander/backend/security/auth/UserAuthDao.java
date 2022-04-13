package com.movierecomander.backend.security.auth;

import java.util.Optional;

public interface UserAuthDao {
    Optional<UserAuth> selectUserAuthByUsername(String username);
}
