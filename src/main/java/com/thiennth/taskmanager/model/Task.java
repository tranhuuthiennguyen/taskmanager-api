package com.thiennth.taskmanager.model;

import java.time.Instant;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Task {
    private final Long          id;
    private       String        title;
    private       String        description;
    private       TaskStatus    status;
    private       TaskPriority  priority;
    private       LocalDate     dueDate;
    private final Long          ownerId;
    private       Long          assigneeId;
    private final Instant       createdAt;
    private       Instant       updatedAt;

    public static Task of(
        String          title,
        String          description,
        TaskStatus      status,
        TaskPriority    priority,
        LocalDate       dueDate,
        Long            ownerId,
        Long            assigneeId
    ) {
        return new Task(null, title, description, status, priority, dueDate, ownerId, assigneeId, null, null);
    }

    public static Task from(
        Long            id,
        String          title,
        String          description,
        TaskStatus      status,
        TaskPriority    priority,
        LocalDate       dueDate,
        Long            ownerId,
        Long            assigneeId,
        Instant         createdAt,
        Instant         updatedAt
    ) {
        return new Task(id, title, description, status, priority, dueDate, ownerId, assigneeId, createdAt, updatedAt);
    }

    public void update(String title, String description, TaskPriority priority, LocalDate dueDate, Long assigneeId) {
        this.title          = title;
        this.description    = description;
        this.priority       = priority;
        this.dueDate        = dueDate;
        this.assigneeId     = assigneeId;
    }
}
