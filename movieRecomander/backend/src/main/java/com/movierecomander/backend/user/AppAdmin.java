package com.movierecomander.backend.user;

public class AppAdmin extends User {
    public AppAdmin() {
    }

    public AppAdmin(Long id, String email, String name, String password) {
        super(id, email, name, password);
    }
}
