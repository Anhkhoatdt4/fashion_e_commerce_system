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

    private static final Map<String, List<String>> MODULE_PERMISSIONS = Map.of(
            "USER_MODULE", List.of("USER_READ", "USER_WRITE"),
            "PRODUCT_MODULE", List.of("PRODUCT_CREATE", "PRODUCT_READ")
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
        Role adminRole = roleRepository.findByRoleName(PredefinedRole.ADMIN_ROLE)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .roleName(PredefinedRole.ADMIN_ROLE)
                            .permissions(new HashSet<>(permissionRepository.findAll()))
                            .build();
                    log.info("Creating ADMIN role with all permissions");
                    return roleRepository.save(role);
                });

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setRoleName("USER");
                    List<String> userPerms = MODULE_PERMISSIONS.get("USER_MODULE");
                    HashSet<Permission> perms = new HashSet<>(permissionRepository.findAllById(userPerms));
                    r.setPermissions(perms);
                    log.info("Creating USER role with USER_MODULE permissions");
                    return roleRepository.save(r);
                });
        Set<Permission> perms = MODULE_PERMISSIONS.get("USER_MODULE").stream()
                .map(name -> permissionRepository.findByName(name)
                        .orElseThrow(() -> new RuntimeException("Permission not found: " + name)))
                .collect(Collectors.toSet());
        adminRole.setPermissions(new HashSet<>(permissionRepository.findAll()));
        userRole.setPermissions(perms);

        roleRepository.save(userRole);

        roleRepository.save(adminRole);
    }
}
