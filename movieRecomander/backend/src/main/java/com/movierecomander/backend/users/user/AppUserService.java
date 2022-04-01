package com.movierecomander.backend.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> getAppUsers()
    {
        return appUserRepository.findAll();
    }

    public void addNewAppUser(AppUser appUser) {
        System.out.println(appUser);
    }
}
