package com.movierecommender.backend.users.admin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppAdminTest {

    @Test
    void getId() {
        AppAdmin appAdmin = new AppAdmin(1L,  "raulmadalin1@yahoo.com", "raul", "madalin");
        var response = appAdmin.getId();
        assertEquals(1, response);
    }

    @Test
    void getPassword() {
        AppAdmin appAdmin = new AppAdmin(1L,  "raulmadalin1@yahoo.com", "raul", "madalin");
        var response = appAdmin.getPassword();
        assertEquals("madalin", response);
    }

    @Test
    void testToString() {
        AppAdmin appAdmin = new AppAdmin(1L,  "raulmadalin1@yahoo.com", "raul", "madalin");
        var response = appAdmin.toString();
        assertEquals("AppAdmin{" +
                                "User{" +
                                    "id=" + 1 +
                                    ", email='" + "raulmadalin1@yahoo.com" + '\'' +
                                    ", name='" + "raul" + '\'' +
                                    ", password='" + "madalin" + '\'' +
                                '}' +
                                '}', response);
    }
}