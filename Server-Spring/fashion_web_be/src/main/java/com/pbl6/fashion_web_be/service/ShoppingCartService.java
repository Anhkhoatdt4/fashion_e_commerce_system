package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.response.ShoppingCartResponse;

import java.util.UUID;

public interface ShoppingCartService {
    ShoppingCartResponse getCartByUser(UUID userId);
    ShoppingCartResponse createCartForUser(UUID userId);
}
