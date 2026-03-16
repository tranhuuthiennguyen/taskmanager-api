package com.thiennth.taskmanager.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.thiennth.taskmanager.model.Role;
import com.thiennth.taskmanager.model.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String email;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Role   role;

    public static UserDetailsImpl from(User user) {
        return new UserDetailsImpl(
            user.getId(),
            user.getEmail(),
            user.getUsername(),
            user.getPassword(),
            user.getFirstName(),
            user.getLastName(),
            user.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override public String getPassword() { return this.password; }
    @Override public String getUsername() { return this.email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
    
}
