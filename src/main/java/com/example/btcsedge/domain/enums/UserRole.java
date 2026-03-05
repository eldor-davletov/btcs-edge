package com.example.btcsedge.domain.enums;

public enum UserRole {
    ADMIN("Admin"),
    USER("User"),
    OPERATOR("Operator");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
