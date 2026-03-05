package com.example.btcsedge;

import com.example.btcsedge.domain.enums.UserRole;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        createUserIfNotExists("admin@btcs.com", "admin123", "Admin", "User", UserRole.ADMIN);
        createUserIfNotExists("user@btcs.com", "user123", "Regular", "User", UserRole.USER);
        createUserIfNotExists("operator@btcs.com", "operator123", "Operator", "User", UserRole.OPERATOR);
        log.info("Default users created successfully.");
    }

    private void createUserIfNotExists(String email, String password, String firstName,
                                       String lastName, UserRole role) {
        if (!userRepository.existsByEmail(email)) {
            User user = User.builder()
                    .username(email)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .firstName(firstName)
                    .lastName(lastName)
                    .userRole(role)
                    .build();
            userRepository.save(user);
        }
    }
}
