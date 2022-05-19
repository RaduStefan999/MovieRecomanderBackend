package com.movierecommender.backend.identity;

import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/identity")
public class IdentityController {

    IdentityService identityService;

    public IdentityController(IdentityService identityService) {
        this.identityService = identityService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<User> getLoggedInUser() {
        var loggedInUser = identityService.getLoggedInUser();

        if (loggedInUser.isEmpty()) {
            throw new BusinessException("Was unable to find logged in user",
                    "Catastrophic Security Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(loggedInUser.get());
    }

}
