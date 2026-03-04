package com.example.btcsedge.dto;


import com.example.btcsedge.domain.enums.RoleName;
import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
    private RoleName role;
}
