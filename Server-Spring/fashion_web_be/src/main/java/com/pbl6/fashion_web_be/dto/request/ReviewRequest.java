package com.pbl6.fashion_web_be.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private UUID productId;
    private int rating;
    private String comment;
}
