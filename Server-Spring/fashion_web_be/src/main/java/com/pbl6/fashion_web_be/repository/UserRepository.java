package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);
}
