package com.thiennth.taskmanager.model;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Tag {
    private final Long id;
    private final String name;
    private final String color;
    private final Long createdBy;
    private final Instant createdAt;

    public static Tag of(String name, String color, Long createdBy) {
        return new Tag(null, name, color, createdBy, null);
    }

    public static Tag from(Long id, String name, String color, Long createdBy, Instant createdAt) {
        return new Tag(id, name, color, createdBy, createdAt);
    }
}
