package com.movierecomander.backend.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    public List<AppUser> getAppUsers()
    {
        return List.of(new AppUser());
    }
}
