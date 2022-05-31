package com.movierecommender.backend.users.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.reviews.Review;
import com.movierecommender.backend.users.User;
import com.movierecommender.backend.security.config.UserRoles;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppUser extends User implements Serializable {

    @NotBlank(message="Gender is mandatory")
    @Pattern(regexp="^(M|F)$", message = "Gender must be M or F.")
    private String gender;

    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
            message = "Birthdate must be the type yyyy-MM-dd")
    private String birthdate;

    @NotBlank(message="Country is mandatory")
    @Pattern(regexp ="^(?=.{2,25}$)(\\w{2,}(\\s?\\w{2,})?)$")
    private String country;

    @NotBlank(message="Phone number is mandatory")
    @Pattern(regexp="^07[0-9]{8}$", message = "Phone number must have length 8 and start with \" 07 \". ")
    private String phoneNumber;

    @Column(length = 500)
    @Size(max = 500, message = "Max length is 500.")
    private String profileImageLink;

    @Transient
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer age;

    @OneToMany(mappedBy = "appUser")
    private Set<Review> ratings;

    @OneToMany(mappedBy = "appUser")
    private Set<Comment> comments;


    public AppUser() {
        super(String.valueOf(UserRoles.USER));
    }

    public AppUser(AppUserDTO appUserDTO)
    {
        super(appUserDTO.getEmail(), appUserDTO.getName(), appUserDTO.getPassword(), String.valueOf(UserRoles.USER));
        this.gender = appUserDTO.getGender();
        this.birthdate = appUserDTO.getBirthdate();
        this.country = appUserDTO.getCountry();
        this.phoneNumber = appUserDTO.getPhoneNumber();
        this.profileImageLink = appUserDTO.getProfileImageLink();
    }

    public AppUser(String gender, String birthdate, String country, String phoneNumber, String profileImageLink) {
        super(String.valueOf(UserRoles.USER));
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public AppUser(String email, String name, String password, String gender, String birthdate, String country,
                   String phoneNumber, String profileImageLink) {
        super(email, name, password, String.valueOf(UserRoles.USER));
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public AppUser(Long id, String email, String name, String password, String gender, String birthdate, String country,
                   String phoneNumber, String profileImageLink) {
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
        return Period.between(this.getInterpretedBirthdate(), LocalDate.now()).getYears();
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    @JsonIgnore
    public LocalDate getInterpretedBirthdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthdate, formatter);
    }

    public void setBirthdate(String birthdate) {
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

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Set<Review> getRatings()
    {
        return ratings;
    }

    public void setRatings(Set<Review> ratings)
    {
        this.ratings = ratings;
    }

    public Set<Comment> getComments()
    {
        return comments;
    }

    public void setComments(Set<Comment> comments)
    {
        this.comments = comments;
    }

    public void isValid(){
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(this.birthdate, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new BusinessException("Invalid date or invalid format.", "Json Format", HttpStatus.BAD_REQUEST);
        }
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(gender, appUser.gender) && Objects.equals(birthdate, appUser.birthdate) && Objects.equals(country, appUser.country) && Objects.equals(phoneNumber, appUser.phoneNumber) && Objects.equals(profileImageLink, appUser.profileImageLink) && Objects.equals(age, appUser.age) && Objects.equals(ratings, appUser.ratings) && Objects.equals(comments, appUser.comments);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode(), gender, birthdate, country, phoneNumber, profileImageLink, age, ratings, comments);
    }
}
