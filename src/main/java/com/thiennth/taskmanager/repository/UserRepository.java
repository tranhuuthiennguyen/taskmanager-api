package com.thiennth.taskmanager.repository;

import java.util.Optional;

import com.thiennth.taskmanager.model.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> update(User user);
    Optional<User> changePassword(User user);
}
