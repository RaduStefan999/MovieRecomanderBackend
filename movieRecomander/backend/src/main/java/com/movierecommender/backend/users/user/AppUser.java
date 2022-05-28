package com.movierecommender.backend.users.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.movierecommender.backend.advice.BusinessException;
import com.movierecommender.backend.comments.Comment;
import com.movierecommender.backend.reviews.Review;
import com.movierecommender.backend.users.User;
import com.movierecommender.backend.security.config.UserRoles;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppUser extends User {

    @NotBlank(message="Gender is mandatory")
    @Pattern(regexp="^(M|F)$")
    private String gender;

    //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    //@JsonFormat(pattern = "yyyy-mm-dd")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    private String birthdate;

    @NotBlank(message="Country is mandatory")
    @Pattern(regexp ="^(?=.{2,25}$)(\\w{2,}(\\s?\\w{2,})?)$")
    private String country;

    @NotBlank(message="Phone number is mandatory")
    @Pattern(regexp="^07[0-9]{8}$")
    private String phoneNumber;

    @Pattern(regexp =
            "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$")
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

    public AppUser(String gender, String birthdate, String country, String phoneNumber, String profileImageLink){
        super(String.valueOf(UserRoles.USER));
        this.gender = gender;

        /*try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("sss");
            this.birthdate = LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            throw new Error("Invalid date format");
        }*/
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public AppUser(String email, String name, String password, String gender, String birthdate, String country,
                   String phoneNumber, String profileImageLink) {
        super(email, name, password, String.valueOf(UserRoles.USER));
        this.gender = gender;
        /**try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("sss");
            this.birthdate = LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            throw new Error("Invalid date format");
        }*/
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public AppUser(Long id, String email, String name, String password, String gender, String birthdate, String country,
                   String phoneNumber, String profileImageLink) {
        super(id, email, name, password, String.valueOf(UserRoles.USER));

        this.gender = gender;
        /*try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("sss");
            this.birthdate = LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            throw new Error("Invalid date format");
        }*/
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profileImageLink = profileImageLink;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Period.between(LocalDate.parse(birthdate, formatter), LocalDate.now()).getYears(); //get interpreted birthdate
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthdate, formatter);
    }

    public void setBirthdate(String birthdate) {
        /*try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            System.out.println("sss");
            this.birthdate = LocalDate.parse(birthdate, formatter);
        } catch (DateTimeParseException e) {
            throw new Error("Invalid date format");
        }*/
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
