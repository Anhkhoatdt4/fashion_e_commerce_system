package com.pbl6.fashion_web_be.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "support_tickets")
@Data
@EqualsAndHashCode(exclude = "messages")
@ToString(exclude = "messages")
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ticket_id")
    private UUID ticketId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile user; // Người tạo ticket

    @Column(name = "subject", nullable = false, length = 200)
    private String subject; // Tiêu đề hỗ trợ

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message; // Nội dung yêu cầu ban đầu

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.OPEN; // Trạng thái ticket

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Quan hệ với các message trả lời (nếu có)
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private java.util.List<SupportMessage> messages;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Status {
        OPEN, IN_PROGRESS, CLOSED
    }
}

@Entity
@Table(name = "support_messages")
@Data
class SupportMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "message_id")
    private UUID messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private SupportTicket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile sender; // Ai gửi message

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
