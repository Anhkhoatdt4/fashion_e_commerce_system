package com.pbl6.fashion_web_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@EqualsAndHashCode(exclude = {"order", "user"})
@ToString(exclude = {"order", "user"})
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile user; // Người thực hiện thanh toán

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod; // Ví dụ: VNPay, Momo, Credit Card

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount; // Số tiền giao dịch

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING; // Trạng thái giao dịch

    @Column(name = "transaction_time", updatable = false)
    private LocalDateTime transactionTime;

    @PrePersist
    protected void onCreate() {
        transactionTime = LocalDateTime.now();
    }

    public enum Status {
        PENDING, SUCCESS, FAILED, REFUNDED
    }
}
