package com.pbl6.fashion_web_be.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipping_methods")
@Data
public class ShippingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shipping_id")
    private UUID shippingId;

    @Column(name = "method_name", nullable = false, length = 100)
    private String methodName;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "estimated_days")
    private Integer estimatedDays;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
