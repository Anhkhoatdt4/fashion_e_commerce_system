package com.pbl6.fashion_web_be.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryRequest {
    String categoryName;
    String description;
    String imageUrl;
    Boolean isActive;
    Integer sortOrder;
    UUID parentCategoryId;
    List<UUID> subcategoryIds;
}
