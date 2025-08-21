package com.pbl6.fashion_web_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private UUID orderItemId;
    private UUID orderId;
    private UUID variantId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
