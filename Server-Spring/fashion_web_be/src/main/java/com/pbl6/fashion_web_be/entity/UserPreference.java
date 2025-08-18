package com.pbl6.fashion_web_be.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_preferences")
@Data
public class UserPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "preference_id")
    private UUID preferenceId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "preferred_size", length = 10)
    private String preferredSize;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "preferred_colors", columnDefinition = "text[]")
    private String[] preferredColors;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "style_preferences", columnDefinition = "text[]")
    private String[] stylePreferences;

    @Column(name = "budget_min", precision = 12, scale = 2)
    private BigDecimal budgetMin;

    @Column(name = "budget_max", precision = 12, scale = 2)
    private BigDecimal budgetMax;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notification_settings", columnDefinition = "jsonb")
    private JsonNode notificationSettings;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

// luu cac tuy chon ca nhan cua user như size, mau sac, phong cach, ngan sach, thiet lap thong bao
// có thể dùng để tích hợp với các hệ thống gợi ý sản phẩm, thông báo khuyến mãi, v.v.