package com.pbl6.fashion_web_be.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceResponse {
    private UUID preferenceId;
    private UUID userId;
    private String preferredSize;
    private String[] preferredColors;
    private String[] stylePreferences;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private String notificationSettings;
}
