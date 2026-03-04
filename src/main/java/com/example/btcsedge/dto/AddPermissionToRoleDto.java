package com.example.btcsedge.dto;


import com.example.btcsedge.domain.enums.PermissionName;
import lombok.Data;

@Data
public class AddPermissionToRoleDto {
    private PermissionName permission;
}
