package com.thiennth.taskmanager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.validation.ValidPassword;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    @ValidPassword
    @JsonProperty("current_password")
    private String currentPassword;
    @ValidPassword
    @JsonProperty("new_password")
    private String newPassword;
}
