package com.movierecomander.backend.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getUsers()
    {
        return appUserService.getAppUsers();
    }

    @PostMapping
    public void registerUser(@RequestBody AppUser appUser)
    {
        appUserService.addNewAppUser(appUser);
    }

}
