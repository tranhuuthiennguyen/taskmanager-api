package com.thiennth.taskmanager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public record UpdateTagToTaskRequest(
    @NotNull
    @JsonProperty("tag_id")
    Long tagId
) {}
