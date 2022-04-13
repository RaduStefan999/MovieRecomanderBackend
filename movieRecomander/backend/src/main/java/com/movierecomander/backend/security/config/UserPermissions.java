package com.movierecomander.backend.security.config;

public enum UserPermissions {
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    MOVIES_READ("movies:read"),
    MOVIES_WRITE("movies:write");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
