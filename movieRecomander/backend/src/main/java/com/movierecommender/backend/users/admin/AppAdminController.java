package com.movierecommender.backend.users.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/admins")
public class AppAdminController {
    private final AppAdminService appAdminService;

    @Autowired
    public AppAdminController(AppAdminService appAdminService) {
        this.appAdminService = appAdminService;
    }

    @ApiOperation(value = "This method is used to get the admins.")     // description added
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AppAdmin>> getAdmins() {
        return ResponseEntity.ok(appAdminService.getAdmins());
    }
}
