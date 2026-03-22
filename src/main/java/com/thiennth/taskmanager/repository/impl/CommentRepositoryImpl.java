package com.thiennth.taskmanager.repository.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.thiennth.taskmanager.model.Comment;
import com.thiennth.taskmanager.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {

    private final NamedParameterJdbcTemplate namedJdbc;

    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, rowNum) -> {
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        return Comment.from(
            rs.getLong("id"),
            rs.getLong("task_id"),
            rs.getLong("author_id"),
            null,
            rs.getString("content"),
            rs.getBoolean("edited"),
            rs.getTimestamp("created_at").toInstant(),
            updatedAt != null ? updatedAt.toInstant() : null
        );
    };

    private static final RowMapper<Comment> COMMENT_WITH_AUTHOR_ROW_MAPPER = (rs, rowNum) -> {
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        return Comment.from(
            rs.getLong("id"),
            rs.getLong("task_id"),
            rs.getLong("author_id"),
            rs.getString("author_username"),
            rs.getString("content"),
            rs.getBoolean("edited"),
            rs.getTimestamp("created_at").toInstant(),
            updatedAt != null ? updatedAt.toInstant() : null
        );
    };

    @Override
    public Comment save(Comment comment) {
        String sql = """
                INSERT INTO comments (task_id, author_id, content)
                VALUES (:taskId, :authorId, :content)
                """;
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("taskId", comment.getTaskId())
            .addValue("authorId", comment.getAuthorId())
            .addValue("content", comment.getContent());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedJdbc.update(sql, params, keyHolder, new String[]{"id", "edited", "created_at"});
            Map<String, Object> keys = keyHolder.getKeys();
            Long id = ((Number) keys.get("id")).longValue();
            Boolean edited = (Boolean) keys.get("edited");
            Instant createdAt = ((Timestamp) keys.get("created_at")).toInstant();
            return Comment.from(id, comment.getTaskId(), comment.getAuthorId(), null, comment.getContent(), edited, createdAt, null);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Comment> getList(Long taskId) {
        String sql = """
                SELECT
                    comments.id,
                    comments.task_id,
                    comments.author_id,
                    users.email AS author_username,
                    comments.content,
                    comments.edited,
                    comments.created_at,
                    comments.updated_at
                FROM comments
                LEFT JOIN users ON users.id = comments.author_id
                WHERE task_id = :taskId
                ORDER BY created_at DESC
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("taskId", taskId);

        try {
            return namedJdbc.query(sql, params, COMMENT_WITH_AUTHOR_ROW_MAPPER);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<Comment> updateContent(Comment comment) {
        String sql = """
                UPDATE comments
                SET content = :content,
                    edited = :edited
                WHERE id = :id
                RETURNING *;
                """;
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("content", comment.getContent())
            .addValue("edited", comment.getEdited())
            .addValue("id", comment.getId());
        try {
            return Optional.of(namedJdbc.queryForObject(sql, params, COMMENT_ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        String sql = """
                DELETE FROM comments
                WHERE id = :id
                """;
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);
        try {
            namedJdbc.update(sql, params);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        String sql = """
                SELECT * FROM comments
                WHERE id = :id
                """;
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id);
        try {
            return Optional.ofNullable(namedJdbc.queryForObject(sql, params, COMMENT_ROW_MAPPER));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    
}
