package com.example.btcsedge.service;



import com.example.btcsedge.domain.enums.PermissionName;
import com.example.btcsedge.dto.CreateRoleDto;
import com.example.btcsedge.dto.RoleDto;

import java.util.List;

public interface RoleService {
    RoleDto create(CreateRoleDto dto);
    List<RoleDto> getAll();
    RoleDto addPermission(Long roleId, PermissionName permission);
}
