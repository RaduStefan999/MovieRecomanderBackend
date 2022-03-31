package com.movierecomander.backend.user;

public class AppUser extends User{
    private String gender;
    private String birthday;
    private String country;
    private String phoneString;

    public AppUser() {
    }

    public AppUser(String gender, String birthday, String country, String phoneString) {
        this.gender = gender;
        this.birthday = birthday;
        this.country = country;
        this.phoneString = phoneString;
    }

    public AppUser(Long id, String email, String name, String password, String gender, String birthday, String country, String phoneString) {
        super(id, email, name, password);
        this.gender = gender;
        this.birthday = birthday;
        this.country = country;
        this.phoneString = phoneString;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
                "gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                ", phoneString='" + phoneString + '\'' +
                '}';
    }
}
