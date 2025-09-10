package com.pbl6.fashion_web_be.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl6.fashion_web_be.dto.request.UserPreferenceRequest;
import com.pbl6.fashion_web_be.dto.response.UserPreferenceResponse;
import com.pbl6.fashion_web_be.entity.UserPreference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {com.fasterxml.jackson.databind.ObjectMapper.class, com.fasterxml.jackson.databind.JsonNode.class})
public interface UserPreferenceMapper {

    @Mapping(source = "user.profileId", target = "userId")
    @Mapping(target = "notificationSettings", expression = "java(pref.getNotificationSettings() != null ? pref.getNotificationSettings().toString() : null)")
    UserPreferenceResponse toResponse(UserPreference pref);

    @Mapping(target = "notificationSettings", expression = "java(convertJson(request.getNotificationSettings()))")
    UserPreference toEntity(UserPreferenceRequest request);

    default JsonNode convertJson(String json) {
        if (json == null) return null;
        try {
            return new ObjectMapper().readTree(json);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON for notificationSettings");
        }
    }
}
