package com.example.btcsedge.domain.enums;

public enum PermissionName {
    // USER permissions
    USER_CREATE("Create user"),
    USER_READ("Read user list"),
    USER_UPDATE("Update user"),
    USER_DELETE("Delete user"),

    // ROLE permissions
    ROLE_CREATE("Create role"),
    ROLE_READ("Read role list"),
    ROLE_UPDATE("Update role"),
    ROLE_DELETE("Delete role"),

    PERMISSION_READ("Read permissions"),

    // BTCS real-world permissions
    ANPR_IMAGE_UPLOAD("Upload ANPR camera image"),
    ANPR_DATA_READ("Read ANPR data"),
    WEIGHBRIDGE_SIGNAL_READ("Read weighbridge signal"),
    REPORT_GENERATE("Generate system reports"),
    ADMIN_PANEL_ACCESS("Access admin panel");



    private final String description;

    PermissionName(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }
}
