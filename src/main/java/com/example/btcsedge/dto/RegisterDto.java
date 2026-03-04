package com.example.btcsedge.dto;


import com.example.btcsedge.domain.enums.RoleName;
import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    private String email;
    private RoleName role;
}

