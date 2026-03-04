package com.example.btcsedge.dto;


import com.example.btcsedge.domain.enums.RoleName;
import lombok.Data;

@Data
public class AddRoleDto {
    private RoleName role;
}
