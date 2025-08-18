package com.pbl6.fashion_web_be.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantResponse {
    private UUID variantId;
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stockQuantity;
    private Boolean isActive;
}