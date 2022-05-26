package com.movierecommender.backend.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.reviews.Review;
import com.movierecommender.backend.users.User;
import com.movierecommender.backend.security.config.UserRoles;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppUser extends User {
    private String gender;
    private LocalDate birthdate;
    private String country;
    private String phoneNumber;
    private String profileImageLink;
    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer age;

    @OneToMany(mappedBy = "appUser")
    Set<Review> ratings;

    @OneToMany(mappedBy = "appUser")
    Set<Comment> comments;


    public AppUser() {
        super(String.valueOf(UserRoles.USER));
    }

    public AppUser(String gender, LocalDate birthdate, String country, String phoneNumber, String profileImageLink) {
        super(String.valueOf(UserRoles.USER));
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public AppUser(String email, String name, String password, String gender, LocalDate birthdate, String country, String phoneNumber, String profileImageLink) {
        super(email, name, password, String.valueOf(UserRoles.USER));
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public AppUser(Long id, String email, String name, String password, String gender, LocalDate birthdate, String country, String phoneNumber, String profileImageLink) {
        super(id, email, name, password, String.valueOf(UserRoles.USER));
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImageLink() {
        return profileImageLink;
    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

    @Transactional
    public void updateAppUser() {
    }

    @Override
    public String toString() {
        return "AppUser{" +
                super.toString() +
                ", gender='" + gender + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
