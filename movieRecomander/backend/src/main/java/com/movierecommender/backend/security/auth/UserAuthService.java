package com.movierecommender.backend.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private final UserAuthDao userAuthDao;

    public UserAuthService(UserAuthDao userAuthDao) {
        this.userAuthDao = userAuthDao;
    }

    //the username by which we do the authentication is the email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userAuthDao
                .selectUserAuthByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("Email %s not found", email))
                );
    }
}
