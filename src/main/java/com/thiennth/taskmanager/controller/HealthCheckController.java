package com.thiennth.taskmanager.controller;

import org.springframework.web.bind.annotation.RestController;

import com.thiennth.taskmanager.dto.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class HealthCheckController {
    
    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<?>> healthCheck(@RequestParam(required = false) String param) {
        return ResponseEntity.ok(ApiResponse.ok("OK", null));
    }
    
}
