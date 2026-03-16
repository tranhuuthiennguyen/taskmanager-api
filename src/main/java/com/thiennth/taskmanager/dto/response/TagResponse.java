package com.thiennth.taskmanager.dto.response;

import com.thiennth.taskmanager.model.Tag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagResponse {
    private final Long id;
    private final String name;
    private final String color;

    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName(), tag.getColor());
    }
}
