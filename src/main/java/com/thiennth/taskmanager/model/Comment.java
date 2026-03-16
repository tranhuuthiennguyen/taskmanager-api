package com.thiennth.taskmanager.model;

import java.time.Instant;

import lombok.Getter;

@Getter
public class Comment {
    private final Long      id;
    private final String    content;
    private final Long      taskId;
    private final Long      userId;
    private final Instant   createdAt;

    private Comment(
        Long      id,
        String    content,
        Long      taskId,
        Long      userId,
        Instant   createdAt
    ) {
        this.id = id;
        this.content = content;
        this.taskId = taskId;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public static Comment of(
        String    content,
        Long      taskId,
        Long      userId
    ) {
        return new Comment(null, content, taskId, userId, null);
    }

    public static Comment from(
        Long      id,
        String    content,
        Long      taskId,
        Long      userId,
        Instant   createdAt
    ) {
        return new Comment(id, content, taskId, userId, createdAt);
    }
}
