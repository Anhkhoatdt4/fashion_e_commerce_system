package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, UUID> {
    Optional<UserPreference> findByUserUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
