package com.thiennth.taskmanager.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.thiennth.taskmanager.model.Tag;
import com.thiennth.taskmanager.repository.TagRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagRepositoryImpl implements TagRepository {

    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedJdbc;

    private static final RowMapper<Tag> TAG_ROW_MAPPER = (rs, rowNum) -> {
        return Tag.from(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("color"),
            rs.getLong("created_by"),
            rs.getTimestamp("created_at").toInstant()
        );
    };

    private static final ResultSetExtractor<Map<Long, List<Tag>>> TAGS_BY_TASK_EXTRACTROR = rs -> {
        Map<Long, List<Tag>> result = new HashMap<>();
        while (rs.next()) {
            Long taskId = rs.getLong("task_id");
            Tag tag = Tag.from(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("color"),
                rs.getLong("created_by"),
                rs.getTimestamp("created_at").toInstant()
            );
            result.computeIfAbsent(taskId, k -> new ArrayList<>()).add(tag);
        }
        return result;
    };

    @Override
    public Map<Long, List<Tag>> getListByTaskIds(List<Long> taskIds) {
        String inSql = String.join(",", Collections.nCopies(taskIds.size(), "?"));
        String sql = String.format("""
                SELECT 
                    tags.id,
                    tags.name,
                    tags.color,
                    tags.created_by,
                    tags.created_at,
                    tasks.id AS task_id
                FROM tags
                INNER JOIN task_tags ON tags.id = task_tags.tag_id
                INNER JOIN tasks ON task_tags.task_id = tasks.id
                WHERE tasks.id IN (%s);
                """, inSql);
        
        try {
            Map<Long, List<Tag>> tags = jdbc.query(sql, TAGS_BY_TASK_EXTRACTROR, taskIds.toArray());
            log.info("Tags list found for task");
            return tags;
        } catch (BadSqlGrammarException e) {
            // Bug in SQL — should never happen in prod, log and rethrow
            log.error("Invalid SQL for tag fetch: {}", e.getMessage());
            throw new RuntimeException("Internal query error", e);
        } catch (DataAccessException e) {
            // DB connectivity, timeout, mapping failure
            log.error("Failed to fetch tags for tasks: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch tags", e);
        }
    }

    @Override
    public List<Tag> getListByTaskId(Long taskId) {
        String sql = """
                SELECT
                    tags.id,
                    tags.name,
                    tags.color,
                    tags.created_by,
                    tags.created_at,
                    tasks.id as task_id
                FROM tags
                INNER JOIN task_tags ON tags.id = task_tags.tag_id
                INNER JOIN tasks ON task_tags.task_id = tasks.id
                WHERE tasks.id = ?
                """;
        try {
            List<Tag> tags = jdbc.query(sql, TAG_ROW_MAPPER, taskId);
            log.info("Tag list found for task with id {}", taskId);
            return tags;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void saveTagsByTaskId(Long taskId, List<Long> tagIds) {

        try {
            if (tagIds != null && !tagIds.isEmpty()) {
                jdbc.batchUpdate(
                    "INSERT INTO task_tags (task_id, tag_id) VALUES (?, ?)",
                    tagIds,
                    tagIds.size(),
                    (ps, tagId) -> {
                        ps.setLong(1, taskId);
                        ps.setLong(2, tagId);
                    }
                );
            }
        } catch (DataAccessException e) {
            log.error("failed", e);
            throw e;
        }
    }

    @Override
    public List<Tag> getList(List<Long> tagIds) {
        String inSql = String.join(",", Collections.nCopies(tagIds.size(), "?"));
        String sql = String.format("""
                SELECT * FROM tags WHERE id IN (%s);
                """, inSql);
        try {
            List<Tag> tags = jdbc.query(sql, TAG_ROW_MAPPER, tagIds.toArray());
            log.info("Tags list found: {}", tagIds);
            return tags;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Override
    public void addTag(Long taskId, Long tagId) {
        String sql = """
                INSERT INTO task_tags (task_id, tag_id) VALUES (:taskId, :tagId)
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("taskId", taskId)
            .addValue("tagId", tagId);

        try {
            namedJdbc.update(sql, params);
            log.info("Add new tag");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void removeTag(Long taskId, Long tagId) {
        String sql = """
                DELETE FROM task_tags
                WHERE task_id = :taskId AND tag_id = :tagId
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("taskId", taskId)
            .addValue("tagId", tagId);

        try {
            namedJdbc.update(sql, params);
            log.info("Tag has been removed from task id {}", taskId);
        } catch (Exception e) {
            throw e;
        }
    }
}
