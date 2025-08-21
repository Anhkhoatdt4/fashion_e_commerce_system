package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.request.CartItemRequest;
import com.pbl6.fashion_web_be.dto.response.CartItemResponse;
import com.pbl6.fashion_web_be.entity.CartItem;
import com.pbl6.fashion_web_be.entity.ProductVariant;
import com.pbl6.fashion_web_be.entity.ShoppingCart;
import com.pbl6.fashion_web_be.mapper.CartItemMapper;
import com.pbl6.fashion_web_be.repository.CartItemRepository;
import com.pbl6.fashion_web_be.repository.ProductRepository;
import com.pbl6.fashion_web_be.repository.ProductVariantRepository;
import com.pbl6.fashion_web_be.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductVariantRepository productVariantRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public CartItemResponse addOrUpdateItem(UUID cartId, CartItemRequest request) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        UUID productVariantId = request.getVariantId();
        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new RuntimeException("Product variant not found"));

        CartItem cartItem = cartItemRepository.findByCartCartId(cartId).stream()
                .filter(i -> i.getVariant().getVariantId().equals(productVariantId))
                .findFirst()
                .orElse(new CartItem());

        cartItem.setCart(cart);
        cartItem.setVariant(productVariant);
        cartItem.setQuantity(request.getQuantity());
        CartItem cardSaved = cartItemRepository.save(cartItem);
        log.debug("Cart item saved: {}", cardSaved);
        return cartItemMapper.toCartItemResponse(cardSaved);
    }

    @Override
    public List<CartItemResponse> getItemsByCart(UUID cartId) {
        return cartItemRepository.findByCartCartId(cartId).stream()
                .map(cartItemMapper::toCartItemResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeItem(UUID cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
