package com.thiennth.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiennth.taskmanager.dto.ApiResponse;
import com.thiennth.taskmanager.dto.request.ChangePasswordRequest;
import com.thiennth.taskmanager.dto.request.UpdateProfileRequest;
import com.thiennth.taskmanager.dto.response.UserResponse;
import com.thiennth.taskmanager.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;






@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMe() {
        UserResponse response = userService.getMe();
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("OK", response));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateMeProfile(@Valid @RequestBody UpdateProfileRequest request) {
        UserResponse response = userService.updateProfile(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("User profile updated successfully", response));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<?>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        UserResponse response = userService.changePassword(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("Password updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        UserResponse response = userService.getUserById(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("OK", response));
    }
}
