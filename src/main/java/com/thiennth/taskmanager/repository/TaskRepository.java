package com.thiennth.taskmanager.repository;

import java.util.List;
import java.util.Optional;

import com.thiennth.taskmanager.model.Paginated;
import com.thiennth.taskmanager.model.Task;
import com.thiennth.taskmanager.model.TaskStatus;

public interface TaskRepository {
    Task save(Task task);
    Paginated<Task> getList(TaskQuery query);
    List<Task> getList(Long assigneeId);
    Optional<Task> findById(Long id);
    Optional<Task> update(Task task);
    Optional<Task> update(Long id, TaskStatus status);
}
