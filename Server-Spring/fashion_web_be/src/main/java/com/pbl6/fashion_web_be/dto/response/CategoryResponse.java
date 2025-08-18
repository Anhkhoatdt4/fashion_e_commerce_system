package com.pbl6.fashion_web_be.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    private UUID categoryId;
    private String categoryName;
    private String description;
    private String imageUrl;
    private Boolean isActive;
    private Integer sortOrder;
    private UUID parentCategoryId;
    private List<CategoryResponse> subcategories;
}
