package com.thiennth.taskmanager.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.model.TaskPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UpdateTaskRequest {

    @Size(min = 1, max = 200)
    @NotBlank
    private String title;

    @Size(max = 2000)
    private String description;

    @NotNull
    private TaskPriority priority;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("assignee_id")
    private Long assigneeId;
}