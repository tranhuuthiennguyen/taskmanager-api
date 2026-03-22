package com.thiennth.taskmanager.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CommentContentRequest(
    @NotEmpty
    String content
) {}
