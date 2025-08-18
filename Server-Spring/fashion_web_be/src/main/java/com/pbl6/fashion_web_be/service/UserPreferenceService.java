package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.UserPreferenceRequest;
import com.pbl6.fashion_web_be.dto.response.UserPreferenceResponse;
import java.util.UUID;

public interface UserPreferenceService {
    UserPreferenceResponse getPreferenceByUser(UUID userId);
    UserPreferenceResponse updatePreference(UUID userId, UserPreferenceRequest request);
}
