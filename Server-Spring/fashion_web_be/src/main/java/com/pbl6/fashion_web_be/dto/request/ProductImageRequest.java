package com.pbl6.fashion_web_be.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageRequest {
    private String imageUrl;
    private Boolean isMain;
}