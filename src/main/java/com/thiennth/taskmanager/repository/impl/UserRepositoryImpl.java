package com.thiennth.taskmanager.repository.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.thiennth.taskmanager.exception.ConflictException;
import com.thiennth.taskmanager.model.Role;
import com.thiennth.taskmanager.model.User;
import com.thiennth.taskmanager.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    
    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedJdbc;

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> User.from(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("email"),
        rs.getString("password"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        Role.valueOf(rs.getString("role")),
        rs.getTimestamp("created_at").toInstant()
    );

    private static final RowMapper<User> USER_NO_PASSWORD_ROW_MAPPER = (rs, rowNum) -> User.from(
        rs.getLong("id"),
        rs.getString("username"),
        rs.getString("email"),
        null,                           // password not selected
        rs.getString("first_name"),
        rs.getString("last_name"),
        Role.valueOf(rs.getString("role")),
        rs.getTimestamp("created_at").toInstant()
    );

    @Override
    public User save(User user) {
        String sql = """
                INSERT INTO users (username, email, password, first_name, last_name, role)
                VALUES (:username, :email, :password, :firstName, :lastName, :role)
                """;

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("username", user.getUsername())
            .addValue("email", user.getEmail())
            .addValue("password", user.getPassword())
            .addValue("firstName", user.getFirstName())
            .addValue("lastName", user.getLastName())
            .addValue("role", user.getRole());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            namedJdbc.update(sql, params, keyHolder, new String[]{"id", "created_at"});

            Map<String, Object> keys= keyHolder.getKeys();
            Long id = ((Number) keys.get("id")).longValue();
            Instant createdAt = ((Timestamp) keys.get("created_at")).toInstant();

            log.info("Saved user with id: {}", id);
            return User.from(id, user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getRole(), createdAt);
        } catch (DuplicateKeyException e) {
            String msg = e.getMessage();
            if (msg.contains("uq_users_email")) {
                throw new ConflictException("Email already exists: " + user.getEmail());
            }
            throw new ConflictException("Duplicate value on a unique field");
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = """
                SELECT id, username, email, password, first_name, last_name, role, created_at
                FROM users WHERE id = ?
                """;

        try {
            User user = jdbc.queryForObject(sql, USER_ROW_MAPPER, id);
            log.info("Found user with id: {}", id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            log.debug("User not found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = """
                SELECT id, username, email, password, first_name, last_name, role, created_at
                FROM users WHERE email = ?
                """;

        try {
            User user = jdbc.queryForObject(sql, USER_ROW_MAPPER, email);
            log.info("Found user with email: {}", email);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            log.debug("User not found with email: {}", email);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> update(User user) {
        String sql = """
                UPDATE users
                SET username    = :username,
                    first_name  = :firstName,
                    last_name   = :lastName
                WHERE id = :id
                RETURNING id, username, email, first_name, last_name, role, created_at
                """;
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("username", user.getUsername())
            .addValue("firstName", user.getFirstName())
            .addValue("lastName", user.getLastName())
            .addValue("id", user.getId());

        try {
            User updatedUser = namedJdbc.queryForObject(sql, params, USER_NO_PASSWORD_ROW_MAPPER);
            log.info("Updated user with id: {}", user.getId());
            return Optional.of(updatedUser);
        } catch (EmptyResultDataAccessException e) {
            log.warn("User not found with id: " + user.getId());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> changePassword(User user) {
        String sql = """
                UPDATE users
                SET password = :password
                WHERE id = :id
                RETURNING id, username, email, first_name, last_name, role, created_at
                """;
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("password", user.getPassword())
            .addValue("id", user.getId());

        try {
            User updatedUser = namedJdbc.queryForObject(sql, params, USER_NO_PASSWORD_ROW_MAPPER);
            log.info("Changed password for user with id: {}", user.getId());
            return Optional.of(updatedUser);
        } catch (EmptyResultDataAccessException e) {
            log.warn("User not found with id: " + user.getId());
            return Optional.empty();
        }
    }
}
