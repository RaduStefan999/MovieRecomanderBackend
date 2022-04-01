package com.movierecomander.backend.users.user;

import com.movierecomander.backend.users.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.Period;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppUser extends User {
    private String gender;
    private LocalDate birthdate;
    private String country;
    private String phoneString;
    @Transient
    private Integer age;

    public AppUser() {
    }

    public AppUser(String gender, LocalDate birthday, String country, String phoneString) {
        this.gender = gender;
        this.birthdate = birthday;
        this.country = country;
        this.phoneString = phoneString;
    }

    public AppUser(Long id, String email, String name, String password, String gender, LocalDate birthdate, String country, String phoneString) {
        super(id, email, name, password);
        this.gender = gender;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneString = phoneString;
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

    public LocalDate getBirthday() {
        return birthdate;
    }

    public void setBirthday(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                super.toString() +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthdate + '\'' +
                ", country='" + country + '\'' +
                ", phoneString='" + phoneString + '\'' +
                '}';
    }
}
