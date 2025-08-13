package com.pbl6.fashion_web_be.dto.request;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @Size(min = 4, message = "Username must be at least 4 characters long")
    @NotBlank(message = "Username is required")
    String username;

    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password is required")
    String password;

    @Email(message = "Email must be a valid email address")
    @NotBlank(message = "Email is required")
    String email;

    @NotBlank(message = "Full name is required")
    String fullName;

    LocalDate dateOfBirth;

    String phone;

    String avatarUrl;

    String gender;
}
