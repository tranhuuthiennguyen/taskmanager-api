package com.thiennth.taskmanager.exception;

public class CommentNotFoundException extends ResourceNotFoundException {
    
    public CommentNotFoundException(Long id) {
        super("Comment", "id = " + id);
    }
}
