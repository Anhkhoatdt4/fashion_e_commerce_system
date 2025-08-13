package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.RoleRequest;
import com.pbl6.fashion_web_be.dto.response.RoleResponse;
import com.pbl6.fashion_web_be.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PermissionMapper.class})
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRoleEntity(RoleRequest roleRequest);

    @Mapping(source = "roleName", target = "roleName")
    @Mapping(source = "permissions", target = "permissions")
    RoleResponse toRoleResponse(Role role);
}
