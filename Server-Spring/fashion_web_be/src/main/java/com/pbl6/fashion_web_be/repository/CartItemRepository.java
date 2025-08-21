package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCartCartId(UUID cartId);
}
