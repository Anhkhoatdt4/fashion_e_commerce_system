package com.pbl6.fashion_web_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {
    private String inventoryId;
    private UUID variantId;
    private Integer quantityAvailable;
    private Integer quantityReserved;
    private LocalDateTime updatedAt;
}
