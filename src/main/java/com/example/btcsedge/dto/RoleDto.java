package com.example.btcsedge.dto;


import com.example.btcsedge.domain.enums.RoleName;
import com.example.btcsedge.domain.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class RoleDto {

    private Long id;
    private RoleName name;
    private Set<String> permissions;

    public static RoleDto from(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(
                        role.getPermissions()
                                .stream()
                                .map(p -> p.getName().name())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}

