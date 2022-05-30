package com.movierecommender.backend.users.user;

import com.movierecommender.backend.users.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AppUserTest {

    @Test
    void getId() {
        AppUser appUser = new AppUser(3L, "raulmadalin3@yahoo.com", "raul", "madalin", "", LocalDate.now().toString(), "", "", "");
        var response = appUser.getId();
        assertEquals(3, response);
    }

    @Test
    void getPassword() {
        AppUser appUser = new AppUser(3L, "raulmadalin3@yahoo.com", "raul", "madalin", "", LocalDate.now().toString(), "", "", "");
        var response = appUser.getPassword();
        assertEquals("madalin", response);
    }

    @Test
    void testToString() {
        AppUser appUser = new AppUser(3L, "raulmadalin3@yahoo.com", "raul", "madalin", "", LocalDate.now().toString(), "", "", "");
        var response = appUser.toString();
        assertEquals("AppUser{" +
                                "User{" +
                                    "id=" + 3 +
                                    ", email='" + "raulmadalin3@yahoo.com" + '\'' +
                                    ", name='" + "raul" + '\'' +
                                    ", password='" + "madalin" + '\'' +
                                '}' +
                                    ", gender='" + "" + '\'' +
                                    ", birthdate='" + "2022-05-30" + '\'' +
                                    ", country='" + "" + '\'' +
                                    ", phoneNumber='" + "" + '\'' +
                                '}', response);
    }
}