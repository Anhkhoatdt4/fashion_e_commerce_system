package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserProfileId(UUID profileId);
    List<Order> findByOrderStatus(Order.OrderStatus status);
    List<Order> findByUserProfileIdAndOrderStatus(UUID profileId, Order.OrderStatus status);
}
