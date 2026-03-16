package com.thiennth.taskmanager.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thiennth.taskmanager.dto.request.ChangePasswordRequest;
import com.thiennth.taskmanager.dto.request.UpdateProfileRequest;
import com.thiennth.taskmanager.dto.response.UserResponse;
import com.thiennth.taskmanager.exception.BadRequestException;
import com.thiennth.taskmanager.exception.UserNotFoundException;
import com.thiennth.taskmanager.model.User;
import com.thiennth.taskmanager.repository.UserRepository;
import com.thiennth.taskmanager.security.AuthUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    public UserResponse getMe() {
        return UserResponse.from(authUtils.getCurrentUserDetails());
    }

    @Transactional
    public UserResponse updateProfile(UpdateProfileRequest request) {
        User user = getCurrentUserEntity();
        user.updateProfile(request.getUsername(), request.getFirstName(), request.getLastName());
        return userRepository.update(user)
			.map(UserResponse::from)
			.orElseThrow(() -> new UserNotFoundException(user.getEmail()));
    }

    @Transactional
    public UserResponse changePassword(ChangePasswordRequest request) {
        User user = getCurrentUserEntity();
		if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
			throw new BadRequestException("Current password is incorrect");
		}

        if (request.getCurrentPassword().equals(request.getNewPassword())) {
            throw new BadRequestException("New password must be different from current passowrd");
        }

		user.changePassword(passwordEncoder.encode(request.getNewPassword()));
		return userRepository.changePassword(user)
			.map(UserResponse::from)
			.orElseThrow(() -> new UserNotFoundException(user.getEmail()));
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
            .map(UserResponse::from)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    private User getCurrentUserEntity() {
        String email = authUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(email));
    }
}
