package com.pbl6.fashion_web_be.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "roles")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(exclude = {"userRoles"})
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "role_id", updatable = false, nullable = false)
    UUID roleId;

    @Column(name = "role_name",unique = true, nullable = false, length = 50)
    String roleName;

    String description;
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToMany(mappedBy = "roles")
    Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
            @JoinTable(
                    name = "role_permissions",
                    joinColumns = @JoinColumn(name = "role_id"),
                    inverseJoinColumns = @JoinColumn(name = "permission_id")
            )
    Set<Permission> permissions;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
