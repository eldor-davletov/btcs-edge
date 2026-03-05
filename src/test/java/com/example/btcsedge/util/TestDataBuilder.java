package com.example.btcsedge.util;

import com.example.btcsedge.domain.enums.UserRole;
import com.example.btcsedge.domain.model.User;

import java.time.LocalDateTime;

public class TestDataBuilder {

    public static User buildUser(Long id, String email, String username, String encodedPassword, UserRole role) {
        return User.builder()
                .id(id)
                .email(email)
                .username(username)
                .password(encodedPassword)
                .firstName("Test")
                .lastName("User")
                .userRole(role)
                .active(true)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
    }

    public static User buildAdminUser() {
        return buildUser(1L, "admin@btcs.com", "admin@btcs.com", "{bcrypt}encoded", UserRole.ADMIN);
    }

    public static User buildRegularUser() {
        return buildUser(2L, "user@btcs.com", "user@btcs.com", "{bcrypt}encoded", UserRole.USER);
    }

    public static User buildOperatorUser() {
        return buildUser(3L, "operator@btcs.com", "operator@btcs.com", "{bcrypt}encoded", UserRole.OPERATOR);
    }
}
