package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.response.InventoryResponse;
import com.pbl6.fashion_web_be.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{variantId}")
    public InventoryResponse getInventory(@PathVariable UUID variantId) {
        return inventoryService.getInventoryByVariant(variantId);
    }

    @PatchMapping("/{variantId}")
    public InventoryResponse updateInventory(
            @PathVariable UUID variantId,
            @RequestParam int deltaAvailable,
            @RequestParam int deltaReserved
    ) {
        return inventoryService.updateInventory(variantId, deltaAvailable, deltaReserved);
    }
}
