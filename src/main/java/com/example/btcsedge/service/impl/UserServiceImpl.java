package com.example.btcsedge.service.impl;

import com.example.btcsedge.domain.enums.RoleName;
import com.example.btcsedge.domain.model.Role;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.dto.CreateUserDto;
import com.example.btcsedge.dto.RegisterDto;
import com.example.btcsedge.dto.UserDto;
import com.example.btcsedge.repo.RoleRepository;
import com.example.btcsedge.repo.UserRepository;
import com.example.btcsedge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto create(CreateUserDto dto) {
        Set<Role> roles = new HashSet<>();
        if (dto.getRole() != null) {
            roleRepository.findByName(dto.getRole()).ifPresent(roles::add);
        }
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .build();
        return UserDto.from(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto addRole(Long userId, RoleName role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        roleRepository.findByName(role).ifPresent(r -> user.getRoles().add(r));
        return UserDto.from(userRepository.save(user));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    @Transactional
    public User register(RegisterDto dto) {
        Set<Role> roles = new HashSet<>();
        if (dto.getRole() != null) {
            roleRepository.findByName(dto.getRole()).ifPresent(roles::add);
        }
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    @Override
    public Page<UserDto> getPage(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDto::from);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }
}
