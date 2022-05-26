package com.movierecommender.backend.identity;

import com.movierecommender.backend.security.facades.IAuthenticationFacade;
import com.movierecommender.backend.users.User;
import com.movierecommender.backend.users.UserService;
import com.movierecommender.backend.users.user.AppUser;
import com.movierecommender.backend.users.user.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdentityService {
    private final IAuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final AppUserService appUserService;

    @Autowired
    public IdentityService(IAuthenticationFacade authenticationFacade, UserService userService, AppUserService appUserService) {
        this.authenticationFacade = authenticationFacade;
        this.userService = userService;
        this.appUserService = appUserService;
    }

    public Optional<User> getLoggedInUser() {
        var userName = (String) authenticationFacade.getAuthentication().getPrincipal();
        return userService.getUserByEmail(userName).stream().findFirst();
    }

    public Optional<AppUser> getLoggedInAppUser() {
        var user = this.getLoggedInUser();
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return appUserService.read(user.get().getId());
    }
}
