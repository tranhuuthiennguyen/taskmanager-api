package com.thiennth.taskmanager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiennth.taskmanager.dto.ApiResponse;
import com.thiennth.taskmanager.dto.PaginatedResponse;
import com.thiennth.taskmanager.dto.request.CommentContentRequest;
import com.thiennth.taskmanager.dto.request.CreateTaskRequest;
import com.thiennth.taskmanager.dto.request.UpdateTagToTaskRequest;
import com.thiennth.taskmanager.dto.request.UpdateTaskRequest;
import com.thiennth.taskmanager.dto.request.UpdateTaskStatusRequest;
import com.thiennth.taskmanager.dto.response.CommentResponse;
import com.thiennth.taskmanager.dto.response.TaskResponse;
import com.thiennth.taskmanager.service.CommentService;
import com.thiennth.taskmanager.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final CommentService commentService;

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
    public ResponseEntity<ApiResponse<TaskResponse>> update(@PathVariable Long id, @Valid @RequestBody UpdateTaskRequest request) {
        TaskResponse response = taskService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Update task successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @Valid @RequestBody UpdateTaskStatusRequest request) {
        TaskResponse response = taskService.update(id, request);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("Data already updated", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Updated task status successfully", response));
    }

    @GetMapping("/assigned")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getList() {
        List<TaskResponse> response = taskService.getList();
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("List of assigned tasks for current user", response));
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<ApiResponse<TaskResponse>> addTagToTask(@Valid @RequestBody UpdateTagToTaskRequest request, @PathVariable Long id) {
        TaskResponse response = taskService.addTag(id, request.tagId());
        if (response == null) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("Tag already added", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Add tag to task successfully", response));
    }
    
    @DeleteMapping("/{id}/tags")
    public ResponseEntity<ApiResponse<TaskResponse>> removeTagFromTask(@Valid @RequestBody UpdateTagToTaskRequest request, @PathVariable Long id) {
        TaskResponse response = taskService.removeTag(id, request.tagId());
        if (response == null) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok("Tag not found", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Remove tag from task successfully", response));
    }

    @GetMapping("/{taskId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getList(@PathVariable Long taskId) {
        List<CommentResponse> response = commentService.getList(taskId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Success", response));
    }
    
    @PostMapping("/{taskId}/comments")
    public ResponseEntity<ApiResponse<?>> postComment(@PathVariable Long taskId, @Valid @RequestBody CommentContentRequest request) {
        CommentResponse response = commentService.add(taskId, request.content());
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Success", response));
    }
    
    @PutMapping("/{taskId}/comments/{id}")
    public ResponseEntity<ApiResponse<?>> editContent(@PathVariable Long taskId, @PathVariable Long id, @Valid @RequestBody CommentContentRequest request) {
        CommentResponse response = commentService.editContent(id, request.content());
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok("Success", response));
    }
}
