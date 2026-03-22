package com.thiennth.taskmanager.model;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
    private final Long      id;
    private final Long      taskId;
    private final Long      authorId;
    private final String    authorUsername;
    private       String    content;
    private       Boolean   edited;
    private final Instant   createdAt;
    private final Instant   updatedAt;

    public static Comment of(
        String    content,
        Long      taskId,
        Long      authorId,
        String    authorUsername,
        Boolean   edited
    ) {
        return new Comment(
            null,
            taskId,
            authorId,
            authorUsername,
            content,
            edited,
            null,
            null
        );
    }

    public static Comment from(
        Long      id,
        Long      taskId,
        Long      authorId,
        String    authorUsername,
        String    content,
        Boolean   edited,
        Instant   createdAt,
        Instant   updatedAt
    ) {
        return new Comment(
            id,
            taskId,
            authorId,
            authorUsername,
            content,
            edited,
            createdAt,
            updatedAt
        );
    }

    public void editContent(String content) {
        this.content = content;
        if (!this.edited) this.edited = true;
    }
}
