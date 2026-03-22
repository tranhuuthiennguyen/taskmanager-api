package com.thiennth.taskmanager.dto.response;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thiennth.taskmanager.model.Comment;

public record CommentResponse(
    Long id,
    @JsonProperty("task_id")
    Long taskId,
    @JsonProperty("author_id")
    Long authorId,
    @JsonProperty("author_username")
    String authorUsername,
    String content,
    Boolean edited,
    @JsonProperty("created_at")
    Instant createdAt,
    @JsonProperty("updated_at")
    Instant updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
            comment.getId(), 
            comment.getTaskId(), 
            comment.getAuthorId(), 
            comment.getAuthorUsername(), 
            comment.getContent(), 
            comment.getEdited(), 
            comment.getCreatedAt(),
            comment.getUpdatedAt());
    }

    public static CommentResponse from(Comment comment, String authorUsername) {
        return new CommentResponse(
            comment.getId(), 
            comment.getTaskId(), 
            comment.getAuthorId(), 
            authorUsername, 
            comment.getContent(), 
            comment.getEdited(), 
            comment.getCreatedAt(),
            comment.getUpdatedAt());
    }
}
