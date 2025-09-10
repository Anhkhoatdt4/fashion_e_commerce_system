package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.*;
import com.pbl6.fashion_web_be.dto.response.*;
import com.pbl6.fashion_web_be.service.AuthenticationService;
import com.pbl6.fashion_web_be.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreateRequest request) {
        try {
            UserResponse result = userService.createUser(request);
            log.info("User created successfully: {}", result);
            return ApiResponse.<UserResponse>builder()
                    .result(result)
                    .message("User created successfully")
                    .build();
        } catch (Exception e) {
            log.error("Error creating user: ", e);
            throw e;
        }
    }

    @PostMapping("/verify")
    public ApiResponse<String> verify(@RequestBody VerifyRequest request) {
        boolean success = userService.verifyEmail(request.getEmail(), request.getCode());
        if (success) {
            return ApiResponse.<String>builder().message("Email verified successfully!").build();
        } else {
            return  ApiResponse.<String>builder().message("Invalid verification code").build();
        }
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("=== ENTERING LOGIN CONTROLLER ===");
        TokenResponse tokenResponse = authenticationService.generateTokenAndRefreshToken(authenticationRequest.getEmail());
        System.out.println("Generated TokenResponse: " + tokenResponse);
        TokenResponse authenticationResponse = TokenResponse.builder()
                .token(tokenResponse.getToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .expiryTime(tokenResponse.getExpiryTime())
                .refreshTokenExpiryTime(tokenResponse.getRefreshTokenExpiryTime())
                .build();
        System.out.println("AuthenticationResponse: " + authenticationResponse);
        return ApiResponse.<TokenResponse>builder()
                .message("Login successful")
                .result(authenticationResponse)
                .build();
    }

    @GetMapping("/profile")
    public ApiResponse<String> getUsername(HttpServletRequest request) {
        String token = authenticationService.getToken(request);
        String username = authenticationService.getUsernameFromToken(token);
        return ApiResponse.<String>builder()
                .message("Username retrieved successfully")
                .result(username)
                .build();
    }

    @PostMapping("/refresh-token")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        String refreshToken = refreshRequest.getToken();
        if (!authenticationService.validateToken(refreshToken)) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(4001)
                    .message("Invalid or expired refresh token")
                    .build();
        }

        String username = authenticationService.getUsernameFromToken(refreshToken);
        if (username == null) {
            return ApiResponse.<AuthenticationResponse>builder()
                    .code(4002)
                    .message("Could not extract username from token")
                    .build();
        }

        TokenResponse tokenResponse = authenticationService.generateTokenAndRefreshToken(refreshRequest.getToken());
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token(tokenResponse.getToken())
                .expiryTime(tokenResponse.getExpiryTime())
                .build();

        return ApiResponse.<AuthenticationResponse>builder()
                .message("Token refreshed successfully")
                .result(authenticationResponse)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestBody LogoutRequest logoutRequest) {
        if (authenticationService.validateToken(logoutRequest.getToken())) {
            authenticationService.logOut(logoutRequest);
            return ApiResponse.<String>builder()
                    .message("Logout successful")
                    .result("You have been logged out successfully.")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(4000)
                    .message("Invalid token")
                    .result("The provided token is invalid or has expired.")
                    .build();
        }
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        String token = request.getToken();
        if (token == null || !token.startsWith("Bearer ")) {
            return ApiResponse.<IntrospectResponse>builder()
                    .code(4000)
                    .message("Missing or malformed Authorization header")
                    .build();
        }

        token = token.replace("Bearer ", "");
        boolean isValid = authenticationService.validateToken(token);

        if (!isValid) {
            return ApiResponse.<IntrospectResponse>builder()
                    .message("Token is invalid or expired")
                    .result(IntrospectResponse.builder()
                            .valid(false)
                            .build())
                    .build();
        }

        String username = authenticationService.getUsernameFromToken(token);
        long expiry = authenticationService.getExpirationTime(token);

        return ApiResponse.<IntrospectResponse>builder()
                .message("Token is valid")
                .result(IntrospectResponse.builder()
                        .valid(true)
                        .username(username)
                        .expiryTime(expiry)
                        .build())
                .build();
    }
}