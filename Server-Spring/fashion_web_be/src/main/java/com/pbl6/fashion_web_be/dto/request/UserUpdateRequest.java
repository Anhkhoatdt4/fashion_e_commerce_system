package com.pbl6.fashion_web_be.dto.request;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.pbl6.fashion_web_be.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 4, message = "Username must be at least 4 characters long")
    String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password;

    @Email(message = "Email must be a valid email address")
    String email;

    String fullName;

    LocalDate dateOfBirth;

    String phone;

    String avatarUrl;

    String gender;
    Set<UUID> roleIds;
}
