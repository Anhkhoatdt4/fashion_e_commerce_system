package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, UUID> {
    // Additional query methods can be defined here if needed
}
