package com.movierecomander.backend.users.admin;

import com.movierecomander.backend.users.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import static com.movierecomander.backend.security.config.UserRoles.*;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppAdmin extends User {
    public AppAdmin() {
        super(String.valueOf(ADMIN));
    }

    public AppAdmin(Long id, String email, String name, String password) {
        super(id, email, name, password, String.valueOf(ADMIN));
    }

    @Override
    public String toString() {
        return "AppAdmin{" +
                super.toString() +
                '}';
    }
}
