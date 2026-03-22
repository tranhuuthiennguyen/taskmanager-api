package com.thiennth.taskmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiennth.taskmanager.dto.response.CommentResponse;
import com.thiennth.taskmanager.exception.CommentNotFoundException;
import com.thiennth.taskmanager.exception.ForbiddenActionException;
import com.thiennth.taskmanager.exception.TaskNotFoundException;
import com.thiennth.taskmanager.model.Comment;
import com.thiennth.taskmanager.model.Task;
import com.thiennth.taskmanager.repository.CommentRepository;
import com.thiennth.taskmanager.repository.TaskRepository;
import com.thiennth.taskmanager.security.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final AuthUtils authUtils;

    @Transactional(readOnly = true)
    public List<CommentResponse> getList(Long taskId) {
        taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        return commentRepository.getList(taskId).stream().map(
            comment -> new CommentResponse(
                comment.getId(), 
                comment.getTaskId(), 
                comment.getAuthorId(), 
                comment.getAuthorUsername(), 
                comment.getContent(), 
                comment.getEdited(), 
                comment.getCreatedAt(),
                comment.getUpdatedAt())
        ).toList();
    }

    @Transactional
    public CommentResponse add(Long taskId, String content) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Comment comment = commentRepository.save(Comment.of(content, task.getId(), authUtils.getCurrentUserId(), null, false));
        return CommentResponse.from(comment);
    }

    @Transactional
    public CommentResponse editContent(Long id, String content) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        if (!comment.getAuthorId().equals(authUtils.getCurrentUserId())) {
            throw new ForbiddenActionException();
        }
        comment.editContent(content);
        Comment updated = commentRepository.updateContent(comment).orElseThrow();
        return CommentResponse.from(updated, authUtils.getCurrentUserEmail());
    }
}
