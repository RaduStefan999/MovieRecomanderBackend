package com.movierecommender.backend.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ResponseStatus(code = HttpStatus.OK, reason = "READ")
    public List<AppUser> getUsers()
    {
        return appUserService.getAppUsers();
    }

    @PostMapping(path = "register")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void registerUser(@Valid @RequestBody AppUser appUser)
    {
        appUserService.addNewAppUser(appUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(code = HttpStatus.OK, reason = "READ")
    public ResponseEntity<Optional<AppUser>> read(@PathVariable("id") Long id) {
        Optional<AppUser> foundUser = appUserService.read(id);
        if (foundUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundUser);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public ResponseEntity<Boolean> update(@RequestBody AppUser appUser, @PathVariable Long id) {

        AppUser updatedUser = appUserService.updateService(id, appUser);
        if (updatedUser==null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(true);
    }

    @DeleteMapping(path = "{appUserId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void deleteUser(@PathVariable("appUserId") Long appUserId) {
        appUserService.deleteAppUser(appUserId);
    }
}
