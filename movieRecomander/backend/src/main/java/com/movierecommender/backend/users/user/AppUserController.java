package com.movierecommender.backend.users.user;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.security.config.UserRoles;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final IdentityService identityService;

    @Autowired
    public AppUserController(AppUserService appUserService, IdentityService identityService) {
        this.appUserService = appUserService;
        this.identityService = identityService;
    }

    @ApiOperation(value = "This method is used to get the users.")      // description added
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok(appUserService.getAppUsers());
    }

    @PostMapping(path = "register")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void registerUser(@Valid @RequestBody AppUser appUser) {
        appUserService.addNewAppUser(appUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<AppUser> read(@PathVariable("id") Long id) {
        Optional<AppUser> foundUser = appUserService.read(id);
        if (foundUser.isEmpty()) {
            throw new BusinessException("User not found", "Invalid data", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(foundUser.get());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UPDATED")
    public void update(@RequestBody AppUserUpdateModel appUserUpdateModel, @PathVariable Long id) {

        if (!this.userCanModify(id)) {
            throw new BusinessException("User can't modify this", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        appUserService.updateService(id, appUserUpdateModel);
    }

    @DeleteMapping(path = "{appUserId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "DELETED")
    public void deleteUser(@PathVariable("appUserId") Long appUserId) {
        appUserService.deleteAppUser(appUserId);
    }

    private boolean userCanModify(Long id) {
        var currentUser = this.identityService.getLoggedInUser();

        return (currentUser.isPresent() &&
                (
                        UserRoles.valueOf(currentUser.get().getRole()) == UserRoles.ADMIN ||
                                currentUser.get().getId().equals(id)
                )
        );
    }
}
