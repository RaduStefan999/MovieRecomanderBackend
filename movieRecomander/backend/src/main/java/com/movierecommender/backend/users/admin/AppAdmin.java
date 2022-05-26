package com.movierecommender.backend.users.admin;

import com.movierecommender.backend.users.User;
import com.movierecommender.backend.security.config.UserRoles;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
public class AppAdmin extends User implements Serializable
{
    public AppAdmin() {
        super(String.valueOf(UserRoles.ADMIN));
    }

    public AppAdmin(String email, String name, String password) {
        super(email, name, password, String.valueOf(UserRoles.ADMIN));
    }

    public AppAdmin(Long id, String email, String name, String password) {
        super(id, email, name, password, String.valueOf(UserRoles.ADMIN));
    }

    @Override
    public String toString() {
        return "AppAdmin{" +
                super.toString() +
                '}';
    }
}
