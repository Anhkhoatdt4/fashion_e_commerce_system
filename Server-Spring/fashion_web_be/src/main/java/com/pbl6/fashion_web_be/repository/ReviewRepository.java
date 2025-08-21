package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByProductProductId(UUID productId);
}
