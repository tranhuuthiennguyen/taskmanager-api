package com.thiennth.taskmanager.dto.response;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.model.Tag;
import com.thiennth.taskmanager.model.Task;
import com.thiennth.taskmanager.model.TaskPriority;
import com.thiennth.taskmanager.model.TaskStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskResponse {
    private final Long id;
    private final String title;
    private final String description;
    private final TaskStatus status;
    private final TaskPriority priority;
    @JsonProperty("due_date")
    private final LocalDate dueDate;
    @JsonProperty("owner_id")
    private final Long ownerId;
    @JsonProperty("assignee_id")
    private final Long assigneeId;
    private final List<TagResponse> tags;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("updated_at")
    private final Instant updatedAt;

    public static TaskResponse from(Task task, List<Tag> tags) {
        return new TaskResponse(
            task.getId(), 
            task.getTitle(), 
            task.getDescription(), 
            task.getStatus(), 
            task.getPriority(), 
            task.getDueDate(), 
            task.getOwnerId(), 
            task.getAssigneeId(), 
            tags.stream().map(tag -> TagResponse.from(tag)).toList(), 
            task.getCreatedAt(), 
            task.getUpdatedAt());
    }
}
