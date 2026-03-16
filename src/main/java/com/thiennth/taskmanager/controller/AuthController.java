package com.thiennth.taskmanager.controller;
import org.springframework.web.bind.annotation.RestController;

import com.thiennth.taskmanager.dto.ApiResponse;
import com.thiennth.taskmanager.dto.request.LoginRequest;
import com.thiennth.taskmanager.dto.request.RegisterRequest;
import com.thiennth.taskmanager.dto.response.LoginResponse;
import com.thiennth.taskmanager.dto.response.UserResponse;
import com.thiennth.taskmanager.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("User registered successfully", response));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("Login successfully", response));
    }
    
}
