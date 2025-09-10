package com.pbl6.fashion_web_be.config;

import com.pbl6.fashion_web_be.constant.PredefinedRole;
import com.pbl6.fashion_web_be.entity.Role;
import com.pbl6.fashion_web_be.entity.UserAccount;
import com.pbl6.fashion_web_be.entity.UserProfile;
import com.pbl6.fashion_web_be.repository.RoleRepository;
import com.pbl6.fashion_web_be.repository.UserRepository;
import com.pbl6.fashion_web_be.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "org.postgresql.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository, UserProfileRepository userProfileRepository, RoleRepository roleRepository) {
        return args -> {
            // Tạo roles nếu chưa tồn tại
            createRoleIfNotExists(roleRepository, PredefinedRole.USER_ROLE, "User role");
            Role adminRole = createRoleIfNotExists(roleRepository, PredefinedRole.ADMIN_ROLE, "Admin role");

            // Tạo user admin nếu chưa có
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                // Tạo UserAccount trước
                UserAccount userAccount = UserAccount.builder()
                        .username(ADMIN_USER_NAME)
                        .email("admin123@gmail.com")
                        .isActive(true)
                        .isVerified(true)
                        .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userAccount = userRepository.save(userAccount);
                
                // Tạo UserProfile
                UserProfile userProfile = UserProfile.builder()
                        .account(userAccount)
                        .fullName("System Administrator")
                        .build();
                
                userProfileRepository.save(userProfile);
                log.warn("Admin user created with default password: admin. Please change it!");
            } else {
                log.info("Admin user already exists");
            }

            log.info("Application initialization completed .....");
        };
    }

    private Role createRoleIfNotExists(RoleRepository roleRepository, String roleName, String description) {
        return roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .roleName(roleName)
                        .description(description)
                        .build()));
    }
}