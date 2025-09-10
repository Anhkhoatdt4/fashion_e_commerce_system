package com.pbl6.fashion_web_be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl6.fashion_web_be.dto.request.UserPreferenceRequest;
import com.pbl6.fashion_web_be.dto.response.UserPreferenceResponse;
import com.pbl6.fashion_web_be.entity.UserProfile;
import com.pbl6.fashion_web_be.entity.UserPreference;
import com.pbl6.fashion_web_be.mapper.UserPreferenceMapper;
import com.pbl6.fashion_web_be.repository.UserPreferenceRepository;
import com.pbl6.fashion_web_be.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository prefRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserPreferenceMapper mapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserPreferenceResponse getPreferenceByUser(UUID userId) {
        return prefRepository.findByUserProfileId(userId)
                .map(mapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updatePreference(UUID userId, UserPreferenceRequest request) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        
        UserPreference pref = prefRepository.findByUserProfileId(userId)
                .orElse(new UserPreference());
        
        // Map request data to existing preference or new one
        UserPreference updatedPref = mapper.toEntity(request);
        updatedPref.setPreferenceId(pref.getPreferenceId()); // Keep existing ID if updating
        updatedPref.setUser(userProfile);
        
        if (request.getNotificationSettings() != null) {
            try {
                updatedPref.setNotificationSettings(objectMapper.readTree(request.getNotificationSettings()));
            } catch (Exception e) {
                throw new RuntimeException("Invalid notificationSettings JSON", e);
            }
        }
        
        return mapper.toResponse(prefRepository.save(updatedPref));
    }
}