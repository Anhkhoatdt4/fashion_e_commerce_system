package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.constant.PredefinedRole;
import com.pbl6.fashion_web_be.dto.request.UserCreateRequest;
import com.pbl6.fashion_web_be.dto.request.UserUpdateRequest;
import com.pbl6.fashion_web_be.dto.response.UserResponse;
import com.pbl6.fashion_web_be.entity.Role;
import com.pbl6.fashion_web_be.entity.UserAccount;
import com.pbl6.fashion_web_be.entity.UserProfile;
import com.pbl6.fashion_web_be.exception.AppException;
import com.pbl6.fashion_web_be.exception.ErrorCode;
import com.pbl6.fashion_web_be.mapper.UserMapper;
import com.pbl6.fashion_web_be.repository.RoleRepository;
import com.pbl6.fashion_web_be.repository.UserRepository;
import com.pbl6.fashion_web_be.repository.UserProfileRepository;
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
    UserProfileRepository userProfileRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;


    public UserResponse createUser(UserCreateRequest userCreateRequest){
        log.info("Start creating user with username: {} , {}", userCreateRequest.getUsername() , userCreateRequest.getEmail());

        try {
            // Tạo UserAccount trước
            UserAccount userAccount = UserAccount.builder()
                    .username(userCreateRequest.getUsername())
                    .email(userCreateRequest.getEmail())
                    .passwordHash(passwordEncoder.encode(userCreateRequest.getPassword()))
                    .isActive(true)
                    .isVerified(false)
                    .build();
                    
            Role userRole = roleRepository.findByRoleNameWithPermissions(PredefinedRole.USER_ROLE)
                            .orElseThrow(() -> {
                                log.error("User role {} not found", PredefinedRole.USER_ROLE);
                                return new AppException(ErrorCode.ROLE_NOT_FOUND);
                            });
            if(userRole.getRoleName().isEmpty()) {
                userRole.setRoleName("USER");
            }
            userAccount.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            userAccount = userRepository.save(userAccount);
            
            // Tạo UserProfile
            UserProfile userProfile = UserProfile.builder()
                    .account(userAccount)
                    .fullName(userCreateRequest.getFullName())
                    .phone(userCreateRequest.getPhone())
                    .build();
            userProfile = userProfileRepository.save(userProfile);
            
            UserResponse response = userMapper.toUserResponse(userProfile);
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
        UserProfile userProfile = userProfileRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        log.info("Updating user with ID: {}", userId);
        userMapper.updateUser(userProfile, userUpdateRequest);
        
        // Update account password and roles if provided
        if (userUpdateRequest.getPassword() != null) {
            userProfile.getAccount().setPasswordHash(passwordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (userUpdateRequest.getRoleIds() != null && !userUpdateRequest.getRoleIds().isEmpty()) {
            var roles = roleRepository.findAllById(userUpdateRequest.getRoleIds());
            if (roles.isEmpty()) {
                throw new AppException(ErrorCode.ROLE_NOT_FOUND);
            }
            userProfile.getAccount().setRoles(new HashSet<>(roles));
            userRepository.save(userProfile.getAccount());
        }
        
        return userMapper.toUserResponse(userProfileRepository.save(userProfile));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(String userId) {
        log.info("Deleting user with ID: {}", userId);
        UserProfile userProfile = userProfileRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userProfileRepository.delete(userProfile);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse> getAllUsers(){
        log.info("Fetching all users");
        List<UserProfile> userProfiles = userProfileRepository.findAll();
        return userProfiles.stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize(("hasAuthority('ADMIN')"))
    public UserResponse getUserById(String userId) {
        log.info("Fetching user with ID: {}", userId);
        UserProfile userProfile = userProfileRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(userProfile);
    }
}
