package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.constant.PredefinedRole;
import com.pbl6.fashion_web_be.dto.request.UserCreateRequest;
import com.pbl6.fashion_web_be.dto.request.UserUpdateRequest;
import com.pbl6.fashion_web_be.dto.response.UserResponse;
import com.pbl6.fashion_web_be.entity.Role;
import com.pbl6.fashion_web_be.entity.User;
import com.pbl6.fashion_web_be.exception.AppException;
import com.pbl6.fashion_web_be.exception.ErrorCode;
import com.pbl6.fashion_web_be.mapper.UserMapper;
import com.pbl6.fashion_web_be.repository.RoleRepository;
import com.pbl6.fashion_web_be.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreateRequest userCreateRequest){
        log.info("Start creating user with username: {} , {}", userCreateRequest.getUsername() , userCreateRequest.getEmail());

        try {
            User user = userMapper.toUserEntity(userCreateRequest);
            user.setPasswordHash(passwordEncoder.encode(userCreateRequest.getPassword()));
            Role userRole = roleRepository.findByRoleName(PredefinedRole.USER_ROLE)
                            .orElseThrow(() -> {
                                log.error("User role {} not found", PredefinedRole.USER_ROLE);
                                return new AppException(ErrorCode.ROLE_NOT_FOUND);
                            });
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            user.setEmail(userCreateRequest.getEmail());
            user.setIsActive(true);
            user = userRepository.save(user);
            UserResponse response = userMapper.toUserResponse(user);
            return response;

        } catch (DataIntegrityViolationException e) {
            log.error("DataIntegrityViolationException when saving user: {}", e.getMessage());
            throw new AppException(ErrorCode.USER_EXISTED);
        } catch (Exception e) {
            log.error("Unexpected error in createUser: {}", e.getMessage(), e);
            throw e;
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest userUpdateRequest){
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        log.info("Updating user with ID: {}", userId);
        userMapper.updateUser(user, userUpdateRequest);
        user.setPasswordHash(passwordEncoder.encode(userUpdateRequest.getPassword()));
        var roles = roleRepository.findAllById(userUpdateRequest.getRoleIds());
        if (roles.isEmpty()) {
            throw new AppException(ErrorCode.ROLE_NOT_FOUND);
        }
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(String userId) {
        log.info("Deleting user with ID: {}", userId);
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers(){
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize(("hasAuthority('ADMIN')"))
    public UserResponse getUserById(String userId) {
        log.info("Fetching user with ID: {}", userId);
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
