package com.pbl6.fashion_web_be.dto.request;

import lombok.*;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    private String productName;
    private String description;
    private String gender;
    private String season;
    private String brandName;
    private UUID categoryId;

    private List<ProductImageRequest> imageUrls;

    private List<VariantCreateRequest> variants;
}
