package com.pbl6.fashion_web_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "brands")
@Data
@EqualsAndHashCode(exclude = {"products"})
@ToString(exclude = {"products"})
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brand_id")
    private UUID brandId;

    @Column(name = "brand_name", unique = true, nullable = false, length = 100)
    private String brandName;

    private String description;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
