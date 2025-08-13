package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.PermissionRequest;
import com.pbl6.fashion_web_be.dto.response.PermissionResponse;
import com.pbl6.fashion_web_be.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
