package com.thiennth.taskmanager.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.model.TaskPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateTaskRequest {
    @NotBlank
    @Size(min = 1, max = 200)
    private String title;
    @Size(max = 2000)
    private String description;
    private TaskPriority priority = TaskPriority.MEDIUM;
    @JsonProperty("due_date")
    private LocalDate dueDate;
    @JsonProperty("assignee_id")
    private Long assigneeId;
    @JsonProperty("tag_ids")
    private List<Long> tagIds;
}
