package com.pbl6.fashion_web_be.repository;

import com.pbl6.fashion_web_be.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<UserAccount> findByUsername(String username);
    Optional<UserAccount> findByEmail(String email);
    @Query("SELECT u FROM UserAccount u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<UserAccount> findByEmailWithRoles(@Param("email") String email);
}
