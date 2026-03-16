package com.thiennth.taskmanager.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.model.User;
import com.thiennth.taskmanager.security.UserDetailsImpl;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {
    private final Long id;
    private final String username;
    private final String email;
    @JsonProperty("first_name")
    private final String firstName;
    @JsonProperty("last_name")
    private final String lastName;
    @Nullable @JsonProperty("created_at")
    private final Instant createdAt;

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getCreatedAt()
        );
    }

    public static UserResponse from(UserDetailsImpl userDetails) {
        return new UserResponse(
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            userDetails.getFirstName(),
            userDetails.getLastName(),
            null
        );
    }
}
