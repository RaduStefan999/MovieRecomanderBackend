package com.movierecomander.backend.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<AppUser> getUsers()
    {
        return appUserService.getAppUsers();
    }

    @PostMapping(path = "register")
    public void registerUser(@RequestBody AppUser appUser)
    {
        appUserService.addNewAppUser(appUser);
        System.out.println(appUser);
    }

    @DeleteMapping(path = "{appUserId}")
    public void deleteUser(@PathVariable("appUserId") Long appUserId) {
        appUserService.deleteAppUser(appUserId);
    }

}
