package com.thiennth.taskmanager.repository;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TaskQuery {
    Long userId;
    int page;
    int limit;
}
