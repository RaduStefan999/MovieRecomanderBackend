package com.movierecommender.backend.identity;

import com.movierecommender.backend.security.facades.IAuthenticationFacade;
import com.movierecommender.backend.users.User;
import com.movierecommender.backend.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentityService {
    private final IAuthenticationFacade authenticationFacade;
    private final UserService userService;

    @Autowired
    public IdentityService(IAuthenticationFacade authenticationFacade, UserService userService) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
    }

    public Optional<User> getLoggedInUser() {
        var userName = (String) authenticationFacade.getAuthentication().getPrincipal();
        return userService.getUserByEmail(userName).stream().findFirst();
    }
}
