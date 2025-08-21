package com.pbl6.fashion_web_be.mapper;

import com.pbl6.fashion_web_be.dto.request.CartItemRequest;
import com.pbl6.fashion_web_be.dto.response.CartItemResponse;
import com.pbl6.fashion_web_be.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponse toCartItemResponse(CartItem cartItem);
    CartItem toCartEntity(CartItemRequest cartItemRequest);
}
