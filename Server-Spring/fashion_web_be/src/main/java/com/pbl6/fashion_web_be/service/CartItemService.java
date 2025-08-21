package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.CartItemRequest;
import com.pbl6.fashion_web_be.dto.response.CartItemResponse;
import java.util.List;
import java.util.UUID;

public interface CartItemService {
    CartItemResponse addOrUpdateItem(UUID cartId, CartItemRequest request);
    List<CartItemResponse> getItemsByCart(UUID cartId);
    void removeItem(UUID cartItemId);
}
