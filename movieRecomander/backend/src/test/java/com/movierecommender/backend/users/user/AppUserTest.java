package com.movierecommender.backend.users.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void getId() {
        AppUser appUser = new AppUser();
        appUser.setId(3L);
        appUser.setEmail("raulmadalin3@yahoo.com");
        appUser.setName("raul");
        appUser.setPassword("madalin");
        appUser.setBirthdate(LocalDate.now().toString());
        appUser.setProfileImageLink("");
        appUser.setPhoneNumber("");
        appUser.setCountry("");
        appUser.setGender("");
        var response = appUser.getId();
        assertEquals(3, response);
    }

    @Test
    void getPassword() {
        AppUser appUser = new AppUser();
        appUser.setId(3L);
        appUser.setEmail("raulmadalin3@yahoo.com");
        appUser.setName("raul");
        appUser.setPassword("madalin");
        appUser.setBirthdate(LocalDate.now().toString());
        appUser.setProfileImageLink("");
        appUser.setPhoneNumber("");
        appUser.setCountry("");
        appUser.setGender("");
        var response = appUser.getPassword();
        assertEquals("madalin", response);
    }

    @Test
    void testToString() {
        AppUser appUser = new AppUser();
        appUser.setId(3L);
        appUser.setEmail("raulmadalin3@yahoo.com");
        appUser.setName("raul");
        appUser.setPassword("madalin");
        appUser.setBirthdate(LocalDate.now().toString());
        appUser.setProfileImageLink("");
        appUser.setPhoneNumber("");
        appUser.setCountry("");
        appUser.setGender("");
        var response = appUser.toString();
        assertEquals("AppUser{" +
                                "User{" +
                                    "id=" + 3 +
                                    ", email='" + "raulmadalin3@yahoo.com" + '\'' +
                                    ", name='" + "raul" + '\'' +
                                    ", password='" + "madalin" + '\'' +
                                '}' +
                                    ", gender='" + "" + '\'' +
                                    ", birthdate='" + LocalDate.now().toString() + '\'' +
                                    ", country='" + "" + '\'' +
                                    ", phoneNumber='" + "" + '\'' +
                                '}', response);
    }
}