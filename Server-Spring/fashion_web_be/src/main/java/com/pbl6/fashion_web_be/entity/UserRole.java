package com.pbl6.fashion_web_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "user_roles")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRole {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    UUID userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    Role role;

    @Column(name = "assigned_at")
    LocalDateTime assignedAt;

    @PrePersist
    protected void onCreate() {
        assignedAt = LocalDateTime.now();
    }
}
