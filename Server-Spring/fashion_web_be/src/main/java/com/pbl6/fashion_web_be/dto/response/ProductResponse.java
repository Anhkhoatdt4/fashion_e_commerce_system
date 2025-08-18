package com.pbl6.fashion_web_be.dto.response;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private UUID productId;
    private String productName;
    private String description;
    private String categoryName;
    private String brandName;
    private Boolean isActive;
    private List<String> images;
    private List<ProductVariantResponse> variants;
}
