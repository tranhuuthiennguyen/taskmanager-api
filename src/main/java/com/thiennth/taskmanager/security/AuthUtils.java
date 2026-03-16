package com.thiennth.taskmanager.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthUtils {

    public UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public UserDetailsImpl getCurrentUserDetails() {
        return (UserDetailsImpl) getCurrentUser();
    }

    public Long getCurrentUserId() {
        return getCurrentUserDetails().getId();
    }

    public String getCurrentUserEmail() {
        return getCurrentUserDetails().getEmail();
    }
}
