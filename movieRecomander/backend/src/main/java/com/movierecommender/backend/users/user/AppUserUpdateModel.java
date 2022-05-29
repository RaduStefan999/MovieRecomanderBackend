package com.movierecommender.backend.users.user;

import java.time.LocalDate;
import java.util.Objects;

public class AppUserUpdateModel {
    private String email;
    private String name;
    private String profileImageLink;

    public AppUserUpdateModel() {
    }

    public AppUserUpdateModel(String email, String name, String profileImageLink) {
        this.email = email;
        this.name = name;
        this.profileImageLink = profileImageLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImageLink() {
        return profileImageLink;
    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserUpdateModel that = (AppUserUpdateModel) o;
        return email.equals(that.email) && name.equals(that.name) && profileImageLink.equals(that.profileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, name, profileImageLink);
    }
}
