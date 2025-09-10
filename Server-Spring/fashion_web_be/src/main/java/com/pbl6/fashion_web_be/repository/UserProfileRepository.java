package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByAccountAccountId(UUID accountId);
    Optional<UserProfile> findByPhone(String phone);
    Optional<UserProfile> findByAccountEmail(String email);
    boolean existsByPhone(String phone);
}
