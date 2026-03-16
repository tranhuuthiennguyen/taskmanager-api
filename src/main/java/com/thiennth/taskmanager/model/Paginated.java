package com.thiennth.taskmanager.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Paginated<T> {
    private final int page;
    private final int limit;
    private final long total;
    private final List<T> data;
}
