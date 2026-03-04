package com.example.btcsedge.service.impl;

import com.example.btcsedge.domain.enums.PermissionName;
import com.example.btcsedge.domain.model.Permission;
import com.example.btcsedge.domain.model.Role;
import com.example.btcsedge.dto.CreateRoleDto;
import com.example.btcsedge.dto.RoleDto;
import com.example.btcsedge.repo.PermissionRepository;
import com.example.btcsedge.repo.RoleRepository;
import com.example.btcsedge.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public RoleDto create(CreateRoleDto dto) {
        Role role = Role.builder()
                .name(dto.getName())
                .permissions(new HashSet<>())
                .build();
        return RoleDto.from(roleRepository.save(role));
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.findAll().stream()
                .map(RoleDto::from)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleDto addPermission(Long roleId, PermissionName permissionName) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        permissionRepository.findByName(permissionName).ifPresent(p -> role.getPermissions().add(p));
        return RoleDto.from(roleRepository.save(role));
    }
}
