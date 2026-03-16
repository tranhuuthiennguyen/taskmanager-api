package com.thiennth.taskmanager.model;

import java.time.Instant;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Task {
    private final Long          id;
    private final String        title;
    private final String        description;
    private final TaskStatus    status;
    private final TaskPriority  priority;
    private final LocalDate     dueDate;
    private final Long          ownerId;
    private final Long          assigneeId;
    private final Instant       createdAt;
    private final Instant       updatedAt;

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
}
