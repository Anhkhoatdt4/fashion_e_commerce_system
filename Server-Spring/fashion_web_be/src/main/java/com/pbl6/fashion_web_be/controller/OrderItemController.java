package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.response.OrderItemResponse;
import com.pbl6.fashion_web_be.entity.OrderItem;
import com.pbl6.fashion_web_be.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders/items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public OrderItemResponse addOrderItem(@RequestBody OrderItem item) {
        return orderItemService.addOrderItem(item);
    }

    @GetMapping("/by-order/{orderId}")
    public List<OrderItemResponse> getItemsByOrder(@PathVariable UUID orderId) {
        return orderItemService.getItemsByOrder(orderId);
    }
}
