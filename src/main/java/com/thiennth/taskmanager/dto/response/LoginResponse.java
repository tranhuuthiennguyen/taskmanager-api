package com.thiennth.taskmanager.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class LoginResponse {
    @JsonProperty("access_token")
    private final String accessToken;
    @JsonProperty("refresh_token")
    private final String refreshToken;

    private LoginResponse(String atk, String rtk) {
        this.accessToken = atk;
        this.refreshToken = rtk;
    }

    public static LoginResponse from(String atk, String rtk) {
        return new LoginResponse(atk, rtk);
    }
}
