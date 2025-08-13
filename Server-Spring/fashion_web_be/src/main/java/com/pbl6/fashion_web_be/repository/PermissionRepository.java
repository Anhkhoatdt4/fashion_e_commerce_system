package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    boolean existsByName(String name);
    Optional<Permission> findByName(String name);
    List<Permission> findAllByNameIn(List<String> names);
}
