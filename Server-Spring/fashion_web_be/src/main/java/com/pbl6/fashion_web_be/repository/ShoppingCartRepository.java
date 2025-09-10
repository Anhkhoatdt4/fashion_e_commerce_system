package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, UUID> {
    Optional<ShoppingCart> findByUserProfileId(UUID profileId);
}
