package com.movierecommender.backend.users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getId() {
        User user = new User((long)5, "raulmadalin5@yahoo.com", "raul", "madalin", "user") {};
        var response = user.getId();
        assertEquals(5, response);
    }

    @Test
    void getPassword() {
        User user = new User((long)5, "raulmadalin5@yahoo.com", "raul", "madalin", "user") {};
        var response = user.getPassword();
        assertEquals("madalin", response);
    }

    @Test
    void testToString() {
        User user = new User((long)5, "raulmadalin5@yahoo.com", "raul", "madalin", "user") {};
        var response = user.toString();
        assertEquals("User{" +
                                "id=" + 5 +
                                ", email='" + "raulmadalin5@yahoo.com" + '\'' +
                                ", name='" + "raul" + '\'' +
                                ", password='" + "madalin" + '\'' +
                                '}', response);
    }
}