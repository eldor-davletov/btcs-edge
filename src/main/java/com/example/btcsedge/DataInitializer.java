package com.example.btcsedge;

import com.example.btcsedge.domain.enums.RoleName;
import com.example.btcsedge.domain.enums.UserRole;
import com.example.btcsedge.domain.model.Role;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.repo.RoleRepository;
import com.example.btcsedge.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.datasource.driver-class-name", havingValue = "org.h2.Driver")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createUserIfNotExists("admin@btcs.com",   "admin123",   "Admin",   "User", UserRole.ADMIN,   RoleName.ADMIN);
        createUserIfNotExists("user@btcs.com",    "user123",    "Regular", "User", UserRole.USER,    RoleName.USER);
        createUserIfNotExists("manager@btcs.com", "manager123", "Manager", "User", UserRole.MANAGER, RoleName.MANAGER);
        log.info("Default users initialised successfully.");
    }

    private void createUserIfNotExists(String email, String password, String firstName,
                                       String lastName, UserRole userRole, RoleName roleName) {
        if (!userRepository.existsByEmail(email)) {
            Set<Role> roles = new HashSet<>();
            Optional<Role> role = roleRepository.findByName(roleName);
            role.ifPresent(roles::add);

            User user = User.builder()
                    .username(email)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .firstName(firstName)
                    .lastName(lastName)
                    .userRole(userRole)
                    .roles(roles)
                    .build();
            userRepository.save(user);
            log.debug("Created user: {}", email);
        }
    }
}

