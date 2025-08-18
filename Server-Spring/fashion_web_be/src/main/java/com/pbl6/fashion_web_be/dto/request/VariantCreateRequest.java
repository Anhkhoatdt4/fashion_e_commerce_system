package com.pbl6.fashion_web_be.dto.request;

import lombok.*;
import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VariantCreateRequest {
    private String color;
    private String size;
    private BigDecimal price;
    private Integer stockQuantity;
}
