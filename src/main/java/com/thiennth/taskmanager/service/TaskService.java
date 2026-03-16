package com.thiennth.taskmanager.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiennth.taskmanager.dto.PaginatedResponse;
import com.thiennth.taskmanager.dto.request.CreateTaskRequest;
import com.thiennth.taskmanager.dto.response.TaskResponse;
import com.thiennth.taskmanager.exception.ForbiddenActionException;
import com.thiennth.taskmanager.exception.ResourceNotFoundException;
import com.thiennth.taskmanager.exception.TaskNotFoundException;
import com.thiennth.taskmanager.model.Paginated;
import com.thiennth.taskmanager.model.Tag;
import com.thiennth.taskmanager.model.Task;
import com.thiennth.taskmanager.model.TaskStatus;
import com.thiennth.taskmanager.repository.TagRepository;
import com.thiennth.taskmanager.repository.TaskQuery;
import com.thiennth.taskmanager.repository.TaskRepository;
import com.thiennth.taskmanager.security.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final AuthUtils authUtils;

    @Transactional(readOnly = true)
    public TaskResponse getById(Long id) {
        Task task = taskRepository.getById(id).orElseThrow(() -> new TaskNotFoundException(id));
        if (!task.getOwnerId().equals(authUtils.getCurrentUserId())) {
            throw new ForbiddenActionException();
        }
        List<Tag> tags = tagRepository.getListByTaskId(task.getId());
        return TaskResponse.from(task, tags);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<TaskResponse> getList(int page, int limit) {
        Long userId = authUtils.getCurrentUserId();

        Paginated<Task> paginated = taskRepository.getList(
            TaskQuery.builder().userId(userId).page(page).limit(limit).build()
        );

        if (paginated.getData().isEmpty()) {
            throw new ResourceNotFoundException("No tasks found");
        }

        List<Long> taskIds = paginated.getData().stream().map(Task::getId).toList();
        Map<Long, List<Tag>> tagsByTaskId = tagRepository.getListByTaskIds(taskIds);

        List<TaskResponse> responses = paginated.getData().stream()
            .map(task -> TaskResponse.from(task, tagsByTaskId.getOrDefault(task.getId(), List.of())))
            .toList();
        
        return PaginatedResponse.of(responses, page, limit, paginated.getTotal());
    }

    @Transactional
    public TaskResponse createNew(CreateTaskRequest request) {
        Task newTask = Task.from(
            null, 
            request.getTitle(), 
            request.getDescription(), 
            TaskStatus.TODO, 
            request.getPriority(), 
            request.getDueDate(), 
            authUtils.getCurrentUserId(),
            request.getAssigneeId(),
            null,
            null
        );
        Task task = taskRepository.save(newTask);
        List<Long> tagIds = request.getTagIds();
        if (tagIds == null || tagIds.isEmpty()) {
            return TaskResponse.from(task, List.of());
        }
        tagRepository.saveTagsByTaskId(task.getId(), tagIds);
        List<Tag> tags = tagRepository.getList(tagIds);
        return TaskResponse.from(task, tags);
    }
}
