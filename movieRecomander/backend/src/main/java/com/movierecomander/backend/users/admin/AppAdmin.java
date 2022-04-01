package com.movierecomander.backend.users.admin;

import com.movierecomander.backend.users.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppAdmin extends User {
    public AppAdmin() {
    }

    public AppAdmin(Long id, String email, String name, String password) {
        super(id, email, name, password);
    }

    @Override
    public String toString() {
        return "AppAdmin{" +
                super.toString() +
                '}';
    }
}
