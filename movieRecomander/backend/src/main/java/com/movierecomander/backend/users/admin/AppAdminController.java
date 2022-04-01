package com.movierecomander.backend.users.admin;

import com.movierecomander.backend.users.user.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/admin")
public class AppAdminController {
    private final AppAdminService appAdminService;

    @Autowired
    public AppAdminController(AppAdminService appAdminService) {
        this.appAdminService = appAdminService;
    }

    @GetMapping
    public List<AppAdmin> getAdmins()
    {
        return appAdminService.getAdmins();
    }
}
