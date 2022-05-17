package com.movierecomander.backend.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserAuthDao userAuthDao;

    @Autowired
    public UserAuthService(UserAuthDao userAuthDao) {
        this.userAuthDao = userAuthDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthDao
                .selectUserAuthByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Username %s not found", username))
                );
    }
}