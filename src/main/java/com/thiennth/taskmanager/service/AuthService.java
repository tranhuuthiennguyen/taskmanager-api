package com.thiennth.taskmanager.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiennth.taskmanager.dto.request.LoginRequest;
import com.thiennth.taskmanager.dto.request.RegisterRequest;
import com.thiennth.taskmanager.dto.response.LoginResponse;
import com.thiennth.taskmanager.dto.response.UserResponse;
import com.thiennth.taskmanager.model.Role;
import com.thiennth.taskmanager.model.User;
import com.thiennth.taskmanager.repository.impl.UserRepositoryImpl;
import com.thiennth.taskmanager.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepositoryImpl userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        String hashed = passwordEncoder.encode(request.getPassword());
        User saved = userRepository.save(User.of(null, request.getEmail(), hashed, null, null, Role.USER));
        return UserResponse.from(saved);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        String accessToken = jwtUtils.generateAccessToken(authentication);
        String refreshToken = jwtUtils.generateRefreshToken(authentication);

        return LoginResponse.from(accessToken, refreshToken);
    }
}
