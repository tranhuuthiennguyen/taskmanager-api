package com.thiennth.taskmanager.dto.request;

import com.thiennth.taskmanager.validation.ValidEmail;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public final class RegisterRequest {
    @ValidEmail
    private String email;
    @NotBlank private String password;
}
