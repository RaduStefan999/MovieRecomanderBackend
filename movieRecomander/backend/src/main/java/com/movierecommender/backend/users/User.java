package com.movierecommender.backend.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Long id;

    @NotBlank(message="Email is mandatory")
    @Pattern(regexp="^(?=.{5,30})[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,5}$", message = "Wrong email")
    //@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    protected String email;

    @NotBlank(message="Name is mandatory")
    @Pattern(regexp ="^(?=.{2,25}$)(\\w{2,}(\\s?\\w{2,})?)$")
    protected String name;

    @NotBlank(message="Password is mandatory")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    protected String password; //password that will be stored as hash

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected String role;

    protected User() {}

    protected User(String role) { this.role = role; }

    protected User(String email, String name, String password, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    protected User(Long id, String email, String name, String password, String role) {
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

    public void validateAndEncryptPassword(PasswordEncoder passwordEncoder) {

        if (!java.util.regex.Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_!@#&()–[{}]:;',?/*~$^+=<>]).{10,50}$",
                this.getPassword()))
        {
            throw new PasswordStrengthException("Password does not match requirements");
        }

        this.setPassword(passwordEncoder.encode(this.getPassword()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && email.equals(user.email) && name.equals(user.name) && role.equals(user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
