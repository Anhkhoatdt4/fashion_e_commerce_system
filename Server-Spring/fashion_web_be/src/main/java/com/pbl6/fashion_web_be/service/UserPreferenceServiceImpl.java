package com.pbl6.fashion_web_be.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pbl6.fashion_web_be.dto.request.UserPreferenceRequest;
import com.pbl6.fashion_web_be.dto.response.UserPreferenceResponse;
import com.pbl6.fashion_web_be.entity.User;
import com.pbl6.fashion_web_be.entity.UserPreference;
import com.pbl6.fashion_web_be.mapper.UserPreferenceMapper;
import com.pbl6.fashion_web_be.repository.UserPreferenceRepository;
import com.pbl6.fashion_web_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {

    private final UserPreferenceRepository prefRepository;
    private final UserRepository userRepository;
    private final UserPreferenceMapper mapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserPreferenceResponse getPreferenceByUser(UUID userId) {
        return prefRepository.findByUserUserId(userId)
                .map(mapper::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public UserPreferenceResponse updatePreference(UUID userId, UserPreferenceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserPreference pref = prefRepository.findByUserUserId(userId).orElse(new UserPreference());
        pref = mapper.toEntity(request);
        if (request.getNotificationSettings() != null) {
            try {
                pref.setNotificationSettings(objectMapper.readTree(request.getNotificationSettings()));
            } catch (Exception e) {
                throw new RuntimeException("Invalid notificationSettings JSON", e);
            }
        }
        pref.setUser(user);
        return mapper.toResponse(prefRepository.save(pref));
    }
}
