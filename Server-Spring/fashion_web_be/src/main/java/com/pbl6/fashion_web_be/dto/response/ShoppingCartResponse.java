package com.pbl6.fashion_web_be.dto.response;

import com.pbl6.fashion_web_be.entity.CartItem;
import com.pbl6.fashion_web_be.entity.ShoppingCart;
import com.pbl6.fashion_web_be.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartResponse {
    private UUID cartId;
    private UUID userId;
    private List<CartItemResponse> items;

    public ShoppingCart toEntity(User user, List<CartItem> cartItems) {
        return ShoppingCart.builder()
                .cartId(cartId)
                .user(user)
                .cartItems(cartItems)
                .build();
    }
}
