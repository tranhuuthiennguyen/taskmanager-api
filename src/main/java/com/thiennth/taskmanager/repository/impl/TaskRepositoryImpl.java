package com.thiennth.taskmanager.repository.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant; 
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.thiennth.taskmanager.model.Paginated;
import com.thiennth.taskmanager.model.Task;
import com.thiennth.taskmanager.model.TaskPriority;
import com.thiennth.taskmanager.model.TaskStatus;
import com.thiennth.taskmanager.repository.TaskQuery;
import com.thiennth.taskmanager.repository.TaskRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TaskRepositoryImpl implements TaskRepository {

    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedJdbc;

    private static final RowMapper<Task> TASK_ROW_MAPPER = (rs, rowNum) -> {
        Date dueDate = rs.getDate("due_date");
        Long assigneeId = rs.getObject("assignee_id", Long.class);

        return Task.from(
            rs.getLong("id"), 
            rs.getString("title"), 
            rs.getString("description"), 
            TaskStatus.valueOf(rs.getString("status")), 
            TaskPriority.valueOf(rs.getString("priority")),
            dueDate != null ? dueDate.toLocalDate() : null,
            rs.getLong("owner_id"),
            assigneeId,
            rs.getTimestamp("created_at").toInstant(),
            rs.getTimestamp("updated_at").toInstant()
        );
    };
        

    @Override
    public Paginated<Task> getList(TaskQuery query) {
        String sql = """
                SELECT *
                FROM tasks
                WHERE owner_id = :ownerId
                ORDER BY created_at DESC
                LIMIT :limit OFFSET :page
                """;

        String countSql = """
                SELECT COUNT(*)
                FROM tasks
                WHERE owner_id = :ownerId
                """;
        
        int page = query.getPage();
        int limit = query.getLimit();
        Long userId = query.getUserId();

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("ownerId", userId)
            .addValue("limit", limit)
            .addValue("page", (page - 1) * limit);

        SqlParameterSource cntParams = new MapSqlParameterSource()
            .addValue("ownerId", userId);

        try {
            List<Task> tasks = namedJdbc.query(sql, params, TASK_ROW_MAPPER);
            long total = namedJdbc.queryForObject(countSql, cntParams, Long.class);
            return new Paginated<Task>(page, limit, total, tasks);
        } catch (EmptyResultDataAccessException e) {
            return new Paginated<Task>(limit, page, 0, null);
        }
    }


    @Override
    public Task save(Task task) {
        String sql = """
                INSERT INTO tasks (title, description, status, priority, due_date, owner_id, assignee_id)
                VALUES (:title, :description, :status, :priority, :dueDate, :ownerId, :assigneeId)
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("title", task.getTitle())
            .addValue("description", task.getDescription())
            .addValue("status", task.getStatus().toString())
            .addValue("priority", task.getPriority() != null ? task.getPriority().toString() : TaskPriority.MEDIUM.toString())
            .addValue("dueDate", task.getDueDate())
            .addValue("ownerId", task.getOwnerId())
            .addValue("assigneeId", task.getAssigneeId());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedJdbc.update(sql, params, keyHolder, new String[]{"id", "created_at", "updated_at"});

            Map<String, Object> keys = keyHolder.getKeys();
            Long id = ((Number) keys.get("id")).longValue();
            Instant createdAt = ((Timestamp) keys.get("created_at")).toInstant();
            Instant updatedAt = ((Timestamp) keys.get("updated_at")).toInstant();

            log.info("Saved new task with id: {}", id);
            return Task.from(id, task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority(), task.getDueDate(), task.getOwnerId(), task.getAssigneeId(), createdAt, updatedAt);
        } catch (Exception e) {
            throw e;
        }
    }


    @Override
    public Optional<Task> findById(Long id) {
        String sql = """
                SELECT * FROM tasks
                WHERE tasks.id = ?
                """;

        try {
            Task task = jdbc.queryForObject(sql, TASK_ROW_MAPPER, id);
            log.info("Found task with id {}", id);
            return Optional.ofNullable(task);
        } catch (DataAccessException e) {
            log.error("Failed to retrive task with id {}", id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Task> update(Task task) {
        String sql = """
                UPDATE tasks
                SET title = :title,
                    description = :description,
                    priority = :priority,
                    due_date = :dueDate,
                    assignee_id = :assigneeId
                WHERE id = :id
                RETURNING *
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("title", task.getTitle())
            .addValue("description", task.getDescription())
            .addValue("priority", task.getPriority().toString())
            .addValue("dueDate", task.getDueDate())
            .addValue("assigneeId", task.getAssigneeId())
            .addValue("id", task.getId());

        try {
            Task updatedTask = namedJdbc.queryForObject(sql, params, TASK_ROW_MAPPER);
            log.info("Updated task with id: {}", task.getId());
            return Optional.ofNullable(updatedTask);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<Task> update(Long id, TaskStatus status) {
        String sql = """
                UPDATE tasks
                SET status = :status
                WHERE id = :id
                RETURNING *
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("status", status.toString())
            .addValue("id", id);
        try {
            Task updated = namedJdbc.queryForObject(sql, params, TASK_ROW_MAPPER);
            log.info("Updated task status with id: {}", id);
            return Optional.ofNullable(updated);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<Task> getList(Long assigneeId) {
        String sql = """
                SELECT * FROM tasks
                WHERE assignee_id = ?
                """;

        try {
            List<Task> tasks = jdbc.query(sql, TASK_ROW_MAPPER, assigneeId);
            log.info("Assigned task list for user with id: {}", assigneeId);
            return tasks;
        } catch (Exception e) {
            throw e;
        }
    }
    
}
