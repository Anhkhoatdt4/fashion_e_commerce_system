package com.pbl6.fashion_web_be.controller;

import com.pbl6.fashion_web_be.dto.request.CartItemRequest;
import com.pbl6.fashion_web_be.dto.response.CartItemResponse;
import com.pbl6.fashion_web_be.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/{cartId}")
    public CartItemResponse addOrUpdate(@PathVariable UUID cartId, @RequestBody CartItemRequest request) {
        return cartItemService.addOrUpdateItem(cartId, request);
    }

    @GetMapping("/{cartId}")
    public List<CartItemResponse> getItems(@PathVariable UUID cartId) {
        return cartItemService.getItemsByCart(cartId);
    }

    @DeleteMapping("/{cartItemId}")
    public void removeItem(@PathVariable UUID cartItemId) {
        cartItemService.removeItem(cartItemId);
    }
}
