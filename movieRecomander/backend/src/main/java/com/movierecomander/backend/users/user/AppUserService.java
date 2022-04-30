package com.movierecomander.backend.users.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AppUser> getAppUsers()
    {
        return appUserRepository.findAll();
    }

    public void addNewAppUser(AppUser appUser) {
        Optional<AppUser> appUserOptional = appUserRepository
                .findAppUserByEmail(appUser.getEmail());

        if (appUserOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));

        appUserRepository.save(appUser);
    }

    public void deleteAppUser(Long appUserId) {
        boolean exists = appUserRepository.existsById(appUserId);
        if (!exists) {
            throw new IllegalStateException("appUser with id " + appUserId + " does not exist");
        }
        appUserRepository.deleteById(appUserId);
    }

    public Optional<AppUser> read(Long id) {
        return appUserRepository.findById(id);
    }

    public AppUser update(Long id, AppUser appUser) {
         appUser.updateAppUser();
         return appUserRepository.getById(id);
    }
}
