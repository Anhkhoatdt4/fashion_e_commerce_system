package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.PermissionRequest;
import com.pbl6.fashion_web_be.dto.response.ApiResponse;
import com.pbl6.fashion_web_be.dto.response.PermissionResponse;
import com.pbl6.fashion_web_be.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(PermissionRequest permissionRequest){
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(permissionRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    ApiResponse<String> deletePermission(@PathVariable String permission) {
        permissionService.deletePermision(permission);
        return ApiResponse.<String>builder()
                .result("Permission deleted successfully")
                .build();
    }
}
