package com.thiennth.taskmanager.dto.request;

import com.thiennth.taskmanager.model.TaskStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateTaskStatusRequest {
    @NotNull
    private TaskStatus status;
}
