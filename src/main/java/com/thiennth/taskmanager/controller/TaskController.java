package com.thiennth.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiennth.taskmanager.dto.ApiResponse;
import com.thiennth.taskmanager.dto.PaginatedResponse;
import com.thiennth.taskmanager.dto.request.CreateTaskRequest;
import com.thiennth.taskmanager.dto.response.TaskResponse;
import com.thiennth.taskmanager.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    
    private final TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<PaginatedResponse<TaskResponse>> getList(@RequestParam int page, @RequestParam int limit) {
        PaginatedResponse<TaskResponse> response = taskService.getList(page, limit);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
    
    @PostMapping("/")
    public ResponseEntity<ApiResponse<TaskResponse>> createNew(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse response = taskService.createNew(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("New task successfully created", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getById(@PathVariable Long id) {
        TaskResponse response = taskService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("Retrieve task successfully", response));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(@PathVariable Long id, @RequestBody String entity) {
        // TO-DO
    }
}
