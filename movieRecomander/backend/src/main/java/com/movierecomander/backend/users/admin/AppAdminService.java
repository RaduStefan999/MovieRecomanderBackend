package com.movierecomander.backend.users.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppAdminService {

    private final AppAdminRepository appAdminRepository;

    @Autowired
    public AppAdminService(AppAdminRepository appAdminRepository) {
        this.appAdminRepository = appAdminRepository;
    }

    public List<AppAdmin> getAdmins() { return appAdminRepository.findAll(); }
}
