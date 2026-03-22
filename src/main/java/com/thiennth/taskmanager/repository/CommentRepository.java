package com.thiennth.taskmanager.repository;

import java.util.List;
import java.util.Optional;

import com.thiennth.taskmanager.model.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
    List<Comment> getList(Long taskId);
    Optional<Comment> findById(Long id);
    Optional<Comment> updateContent(Comment comment);
    void delete(Long id);
}
