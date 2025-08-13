package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.PermissionRequest;
import com.pbl6.fashion_web_be.dto.request.RoleRequest;
import com.pbl6.fashion_web_be.dto.response.PermissionResponse;
import com.pbl6.fashion_web_be.dto.response.RoleResponse;
import com.pbl6.fashion_web_be.entity.Permission;
import com.pbl6.fashion_web_be.mapper.PermissionMapper;
import com.pbl6.fashion_web_be.mapper.RoleMapper;
import com.pbl6.fashion_web_be.repository.PermissionRepository;
import com.pbl6.fashion_web_be.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    public void deletePermision(String role){
        permissionRepository.deleteById(role);
    }
}
