package com.movierecommender.backend.users;

import com.movierecommender.backend.users.user.AppUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;

    @NotBlank(message="Email is mandatory")
    @Pattern(regexp="^[A-Za-z0-9+_.-]+@(.+)$", message = "cd")
    private String email;

    @NotBlank(message="Name is mandatory")
    private String name;

    @Pattern(regexp="(.)*.{8,20}$",message="length must be 8")
    @NotBlank(message="Password is mandatory")
    private String password; //password that will be stored as hash

    @NotBlank(message="Role is mandatory")
    private String role;

    public User() {}

    public User(String role) { this.role = role; }

    public User(String email, String name, String password, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String email, String name, String password, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void update(AppUser appUser, Long id){
        this.email = appUser.getEmail();
        this.name = appUser.getName();
        this.password = appUser.getPassword();
        this.role = appUser.getRole();
    }
}
