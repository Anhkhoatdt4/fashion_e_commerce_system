package com.pbl6.fashion_web_be.dto.response;

import java.util.Set;

import com.pbl6.fashion_web_be.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userId;
    String username;
    String email;
    String fullName;
    boolean isActive;
    Set<RoleResponse> roles;
}
