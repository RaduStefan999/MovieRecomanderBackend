package com.movierecommender.backend.users.user;

import com.movierecommender.backend.advice.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new BusinessException("email taken", "Register error", HttpStatus.CONFLICT);
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


    public void updateService(Long id,AppUserUpdateModel appUserUpdateModel) {
        var foundUser = appUserRepository.findAppUserById(id);
        if (foundUser.isEmpty()) {
            throw new BusinessException("User not found", "Invalid data", HttpStatus.NOT_FOUND);
        }

        if (appUserRepository.findAppUserByEmail(appUserUpdateModel.getEmail()).isPresent()) {
            throw new BusinessException("Email is already present", "Invalid data", HttpStatus.BAD_REQUEST);
        }

        var user = foundUser.get();
        user.setName(appUserUpdateModel.getName());
        user.setEmail(appUserUpdateModel.getEmail());
        user.setProfileImageLink(appUserUpdateModel.getProfileImageLink());

        appUserRepository.save(user);
    }
}
