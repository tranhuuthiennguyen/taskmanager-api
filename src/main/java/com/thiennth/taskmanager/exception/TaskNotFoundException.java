package com.thiennth.taskmanager.exception;

public class TaskNotFoundException extends ResourceNotFoundException {
    
    public TaskNotFoundException(Long id) {
        super("Task", "id = " + id);
    }
}
