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
public class CartItemResponse {
    private UUID cartItemId;
    private UUID cartId;
    private UUID variantId;
    private Integer quantity;
}
