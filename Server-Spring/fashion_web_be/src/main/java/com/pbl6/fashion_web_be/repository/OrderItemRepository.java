package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderOrderId(UUID orderId);
}
