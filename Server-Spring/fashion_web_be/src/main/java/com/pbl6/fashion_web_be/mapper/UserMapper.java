package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.UserCreateRequest;
import com.pbl6.fashion_web_be.dto.request.UserUpdateRequest;
import com.pbl6.fashion_web_be.dto.response.UserResponse;
import com.pbl6.fashion_web_be.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "profileId", target = "userId")
    @Mapping(source = "account.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.isActive", target = "isActive")
    @Mapping(source = "account.roles", target = "roles")
    UserResponse toUserResponse(UserProfile userProfile);
    
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "preference", ignore = true)
    @Mapping(target = "shoppingCarts", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "wishlists", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    UserProfile toUserEntity(UserCreateRequest request);
    
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "profileId", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "preference", ignore = true)
    @Mapping(target = "shoppingCarts", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "wishlists", ignore = true)
    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    void updateUser(@MappingTarget UserProfile userProfile, UserUpdateRequest request);
}
