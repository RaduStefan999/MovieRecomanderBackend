package com.movierecomander.backend.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/user")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<AppUser> getUsers()
    {
        return appUserService.getAppUsers();
    }

    @PostMapping(path = "register")
    public void registerUser(@Valid @RequestBody AppUser appUser)
    {
        appUserService.addNewAppUser(appUser);
        System.out.println(appUser);
    }

    @DeleteMapping(path = "{appUserId}")
    public void deleteUser(@PathVariable("appUserId") Long appUserId) {
        appUserService.deleteAppUser(appUserId);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<AppUser>> read(@PathVariable("id") Long id) {
        Optional<AppUser> foundUser = appUserService.read(id);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundUser);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUser> update(@RequestBody AppUser appUser, @PathVariable Long id) {
        AppUser updatedUser = appUserService.update(id, appUser);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }
}
