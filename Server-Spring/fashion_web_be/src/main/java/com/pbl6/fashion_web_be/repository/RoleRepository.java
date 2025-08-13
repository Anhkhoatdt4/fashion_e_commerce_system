package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    boolean existsRoleByRoleName(String name);
    Optional<Role> findByRoleName(String roleName);

    @Query("SELECT r FROM Role r LEFT JOIN FETCH r.permissions WHERE r.roleName = :roleName")
    Optional<Role> findByRoleNameWithPermissions(String roleName);
}
