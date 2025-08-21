package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.response.InventoryResponse;
import com.pbl6.fashion_web_be.entity.Inventory;
import com.pbl6.fashion_web_be.repository.InventoryRepository;
import com.pbl6.fashion_web_be.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryResponse getInventoryByVariant(UUID variantId) {
        return inventoryRepository.findByVariantVariantId(variantId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    @Transactional
    public InventoryResponse updateInventory(UUID variantId, int deltaAvailable, int deltaReserved) {
        Inventory inv = inventoryRepository.findByVariantVariantId(variantId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inv.setQuantityAvailable(inv.getQuantityAvailable() + deltaAvailable);
        inv.setQuantityReserved(inv.getQuantityReserved() + deltaReserved);
        return toResponse(inventoryRepository.save(inv));
    }

    private InventoryResponse toResponse(Inventory inv) {
        InventoryResponse res = new InventoryResponse();
        res.setInventoryId(inv.getInventoryId());
        res.setVariantId(inv.getVariant().getVariantId());
        res.setQuantityAvailable(inv.getQuantityAvailable());
        res.setQuantityReserved(inv.getQuantityReserved());
        res.setUpdatedAt(inv.getUpdatedAt());
        return res;
    }
}
