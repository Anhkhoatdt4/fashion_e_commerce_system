package com.pbl6.fashion_web_be.service;

import com.pbl6.fashion_web_be.dto.response.ShoppingCartResponse;
import com.pbl6.fashion_web_be.entity.ShoppingCart;
import com.pbl6.fashion_web_be.entity.UserProfile;
import com.pbl6.fashion_web_be.mapper.CartItemMapper;
import com.pbl6.fashion_web_be.repository.ShoppingCartRepository;
import com.pbl6.fashion_web_be.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository cartRepo;
    private final CartItemMapper cartItemMapper;
    private final UserProfileRepository userProfileRepo;

    @Override
    @Transactional
    public ShoppingCartResponse getCartByUser(UUID userId) {
        UserProfile userProfile = userProfileRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
        ShoppingCart cart = cartRepo.findByUserProfileId(userId)
                .orElseGet(() -> {
                    log.debug("No cart found for user {}, creating a new one", userId);
                    return createCartForUser(userId).toEntity(userProfile, Collections.emptyList());
                });
        ShoppingCartResponse res = new ShoppingCartResponse();
        res.setCartId(cart.getCartId());
        res.setUserId(userId);
        res.setItems(cart.getCartItems().stream()
                .map(cartItemMapper::toCartItemResponse)
                .collect(Collectors.toList()));
        return res;
    }

    @Override
    @Transactional
    public ShoppingCartResponse createCartForUser(UUID userId) {
        UserProfile user = userProfileRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        cart = cartRepo.save(cart);

        ShoppingCartResponse res = new ShoppingCartResponse();
        res.setCartId(cart.getCartId());
        res.setUserId(userId);
        res.setItems(Collections.emptyList());
        return res;
    }
}
