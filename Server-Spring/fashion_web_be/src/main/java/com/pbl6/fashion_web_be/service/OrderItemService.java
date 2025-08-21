package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.response.OrderItemResponse;
import com.pbl6.fashion_web_be.entity.OrderItem;
import com.pbl6.fashion_web_be.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository repo;

    @Transactional
    public OrderItemResponse addOrderItem(OrderItem item) {
        return toResponse(repo.save(item));
    }
    public List<OrderItemResponse> getItemsByOrder(UUID orderId) {
        return repo.findById(orderId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private OrderItemResponse toResponse(OrderItem item) {
        OrderItemResponse res = new OrderItemResponse();
        res.setOrderItemId(item.getOrderItemId());
        res.setOrderId(item.getOrder().getOrderId());
        res.setVariantId(item.getVariant().getVariantId());
        res.setQuantity(item.getQuantity());
        res.setUnitPrice(item.getUnitPrice());
        res.setTotalPrice(item.getTotalPrice());
        return res;
    }
}
