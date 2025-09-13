package com.pbl6.fashion_web_be.config;

import com.pbl6.fashion_web_be.constant.PredefinedRole;
import com.pbl6.fashion_web_be.entity.Permission;
import com.pbl6.fashion_web_be.entity.Role;
import com.pbl6.fashion_web_be.repository.PermissionRepository;
import com.pbl6.fashion_web_be.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner{

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    public static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";
    public static final String STORE_ADMIN_ROLE = "STORE_ADMIN";
    public static final String CUSTOMER_ROLE = "CUSTOMER";

    private static final Map<String, List<String>> MODULE_PERMISSIONS = Map.of(
            // Module quản lý cửa hàng
            "SHOP_MANAGEMENT", List.of("SHOP_CREATE", "SHOP_READ", "SHOP_UPDATE", "SHOP_DELETE"),
            "PRODUCT_MANAGEMENT", List.of("PRODUCT_CREATE", "PRODUCT_READ", "PRODUCT_UPDATE", "PRODUCT_DELETE"),
            "ORDER_MANAGEMENT", List.of("ORDER_CREATE", "ORDER_READ", "ORDER_UPDATE", "ORDER_DELETE"),
            "CATEGORY_MANAGEMENT", List.of("CATEGORY_CREATE", "CATEGORY_READ", "CATEGORY_UPDATE", "CATEGORY_DELETE"),
            "INVENTORY_MANAGEMENT", List.of("INVENTORY_READ", "INVENTORY_UPDATE"),

            // Module admin hệ thống (Super Admin)
            "USER_MANAGEMENT", List.of("USER_CREATE", "USER_READ", "USER_UPDATE", "USER_DELETE"),
            "ROLE_MANAGEMENT", List.of("ROLE_CREATE", "ROLE_READ", "ROLE_UPDATE", "ROLE_DELETE"),

            // Module khách hàng
            "CUSTOMER_ACCESS", List.of("PROFILE_UPDATE", "ORDER_VIEW", "CART_MANAGE", "REVIEW_CREATE")
    );

    @Override
    public void run(String... args) {
        log.info("Initializing permissions and roles...");
        registerModulePermissions();
        createDefaultRoles();
    }

    private void registerModulePermissions() {
        MODULE_PERMISSIONS.forEach(((module, perms) ->  {
            for (String permName : perms){
                if (permissionRepository.existsByName(permName)) {
                    log.info("Permission: {} already exists for module: {}", permName, module);
                    continue;
                }
                permissionRepository.findByName(permName).orElseGet(() -> {
                    Permission p = new Permission();
                    p.setName(permName);
                    p.setDescription(module + " - " + permName);
                    return permissionRepository.save(p);
                });
            }
        }));
    }

    private void createDefaultRoles() {
        // ADMIN role với tất cả permissions
        Role adminRole = roleRepository.findByRoleName(PredefinedRole.ADMIN_ROLE)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .roleName(PredefinedRole.ADMIN_ROLE)
                            .description("Super Administrator - Full system access")
                            .permissions(new HashSet<>(permissionRepository.findAll()))
                            .build();
                    log.info("Creating ADMIN role with all permissions");
                    return roleRepository.save(role);
                });

        // USER role với customer permissions
        Role userRole = roleRepository.findByRoleName(PredefinedRole.USER_ROLE)
                .orElseGet(() -> {
                    List<String> userPerms = MODULE_PERMISSIONS.get("CUSTOMER_ACCESS");
                    Set<Permission> permissions = userPerms.stream()
                            .map(name -> permissionRepository.findByName(name)
                                    .orElseThrow(() -> new RuntimeException("Permission not found: " + name)))
                            .collect(Collectors.toSet());
                    
                    Role role = Role.builder()
                            .roleName(PredefinedRole.USER_ROLE)
                            .description("Customer")
                            .permissions(permissions)
                            .build();
                    log.info("Creating USER role with customer permissions");
                    return roleRepository.save(role);
                });
    }
}
