package com.movierecommender.backend.users.user;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.identity.IdentityService;
import com.movierecommender.backend.security.config.UserRoles;
import com.movierecommender.backend.security.jwt.JwtConfig;
import com.movierecommender.backend.security.utils.TokenSigner;
import com.movierecommender.backend.users.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/users")
public class AppUserController {

    private final AppUserService appUserService;
    private final IdentityService identityService;

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public AppUserController(AppUserService appUserService, IdentityService identityService,
                             JwtConfig jwtConfig, SecretKey secretKey)
    {
        this.appUserService = appUserService;
        this.identityService = identityService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @ApiOperation(value = "This method is used to get the users.")      // description added
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok(appUserService.getAppUsers());
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

    @PostMapping(path = "register")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public void registerUser(@Valid @RequestBody AppUserDTO appUserDTO) {
        appUserDTO.isValid();
        appUserService.addNewAppUser(new AppUser(appUserDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Void> update(@RequestBody AppUserUpdateModel appUserUpdateModel, @PathVariable Long id) {
        if (!this.userCanModify(id)) {
            throw new BusinessException("User can't modify this", "Invalid permission", HttpStatus.FORBIDDEN);
        }

        boolean loggedInIsUser = loggedInIsUser();

        var updatedUser = appUserService.updateService(id, appUserUpdateModel);

        if (updatedUser.isEmpty()) {
            throw new BusinessException("Failed to update user", "Internal update error",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return loggedInIsUser ?
                ResponseEntity.noContent().headers(getTokenForChangedUser(updatedUser.get())).build()
                    :
                ResponseEntity.noContent().build();

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

    private HttpHeaders getTokenForChangedUser(User changedUser) {
        TokenSigner tokenSigner = new TokenSigner(jwtConfig, secretKey);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(tokenSigner.getAuthorizationHeader(),
                tokenSigner.getToken(changedUser));

        return responseHeaders;
    }

    private boolean loggedInIsUser() {
        var currentUser = this.identityService.getLoggedInUser();
        return currentUser.isPresent() &&
                UserRoles.valueOf(currentUser.get().getRole()) == UserRoles.USER;
    }
}
