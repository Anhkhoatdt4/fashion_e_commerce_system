package com.pbl6.fashion_web_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private UUID reviewId;
    private UUID productId;
    private UUID userId;
    private int rating;
    private String comment;
}
