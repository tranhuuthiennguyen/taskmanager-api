package com.thiennth.taskmanager.model;

import java.time.Instant;

import lombok.Getter;

@Getter
public class User {
    private final Long      id;
    private final String    email;
    private       String    username;
    private       String    password;
    private       String    firstName;
    private       String    lastName;
    private final Role      role;
    private final Instant   createdAt;

    private User(
        Long    id,
        String  username,
        String  email,
        String  password,
        String  fName,
        String  lName,
        Role    role,
        Instant createdAt
    ) {
        this.id         = id;
        this.username   = username;
        this.email      = email;
        this.password   = password;
        this.firstName  = fName;
        this.lastName   = lName;
        this.role       = role;
        this.createdAt  = createdAt;
    }

    public static User of(
        String username,
        String email,
        String password,
        String fName,
        String lName,
        Role   role
    ) {
        return new User(null, username, email, password, fName, lName, role, null);
    }

    public static User from(
        Long    id,
        String  username,
        String  email,
        String  password,
        String  fName,
        String  lName,
        Role    role,
        Instant createdAt
    ) {
        return new User(id, username, email, password, fName, lName, role, createdAt);
    }

    public void updateProfile(String username, String fName, String lName) {
        this.username = username;
        this.firstName = fName;
        this.lastName = lName;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
