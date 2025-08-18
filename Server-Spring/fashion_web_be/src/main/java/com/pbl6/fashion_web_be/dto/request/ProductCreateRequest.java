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
    private UUID brandId;
    private UUID categoryId;

    private List<String> imageUrls;

    private List<VariantCreateRequest> variants;
}
