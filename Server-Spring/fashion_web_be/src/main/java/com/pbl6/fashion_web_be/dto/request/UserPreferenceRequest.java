package com.pbl6.fashion_web_be.dto.request;

import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPreferenceRequest {
    private UUID userId;
    private String preferredSize;
    private String[] preferredColors;
    private String[] stylePreferences;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private String notificationSettings; // JSON string
}
