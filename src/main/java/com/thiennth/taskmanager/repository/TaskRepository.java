package com.thiennth.taskmanager.repository;

import java.util.Optional;

import com.thiennth.taskmanager.model.Paginated;
import com.thiennth.taskmanager.model.Task;

public interface TaskRepository {
    Task save(Task task);
    Paginated<Task> getList(TaskQuery query);
    Optional<Task> getById(Long id);
}
