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
                WHERE owner_id = ?
                ORDER BY created_at DESC
                LIMIT ? OFFSET ?
                """;

        String countSql = """
                SELECT COUNT(*)
                FROM tasks
                WHERE owner_id = ?
                """;
        
        int page = query.getPage();
        int limit = query.getLimit();
        Long userId = query.getUserId();

        try {
            List<Task> tasks = jdbc.query(sql, TASK_ROW_MAPPER, query.getUserId(), limit, (page - 1) * limit);
            long total = jdbc.queryForObject(countSql, Long.class, userId);
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
    public Optional<Task> getById(Long id) {
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
    
}
