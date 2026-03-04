package com.example.btcsedge.service;



import com.example.btcsedge.domain.enums.RoleName;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.dto.CreateUserDto;
import com.example.btcsedge.dto.RegisterDto;
import com.example.btcsedge.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDto create(CreateUserDto dto);
    List<UserDto> getAll();
    UserDto addRole(Long userId, RoleName role);
    User findByUsername(String username);
    User register(RegisterDto dto);
    Page<UserDto> getPage(Pageable pageable);
    long countUsers();
}
