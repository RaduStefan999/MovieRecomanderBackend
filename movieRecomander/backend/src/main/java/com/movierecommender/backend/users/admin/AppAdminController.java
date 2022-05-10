package com.movierecommender.backend.users.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/admin")
public class AppAdminController {
    private final AppAdminService appAdminService;

    @Autowired
    public AppAdminController(AppAdminService appAdminService) {
        this.appAdminService = appAdminService;
    }

    @GetMapping
    public ResponseEntity<List<AppAdmin>> getAdmins() {
        return ResponseEntity.ok(appAdminService.getAdmins());
    }
}
