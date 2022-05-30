package com.movierecommender.backend.users;

public class PasswordStrengthException extends IllegalStateException {
    public PasswordStrengthException(String message) {
        super(message);
    }
}
