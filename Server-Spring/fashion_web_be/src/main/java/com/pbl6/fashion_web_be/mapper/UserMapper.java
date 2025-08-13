package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.UserCreateRequest;
import com.pbl6.fashion_web_be.dto.request.UserUpdateRequest;
import com.pbl6.fashion_web_be.dto.response.UserResponse;
import com.pbl6.fashion_web_be.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserResponse toUserResponse(User user);
    User toUserEntity(UserCreateRequest request);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
