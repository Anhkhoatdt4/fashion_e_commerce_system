package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.User;
import com.pbl6.fashion_web_be.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {
    List<UserAddress> findByUserUserId(UUID userId);
}
