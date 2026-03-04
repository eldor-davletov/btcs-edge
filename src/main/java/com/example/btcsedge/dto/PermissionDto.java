package com.example.btcsedge.dto;


import com.example.btcsedge.domain.enums.PermissionName;
import com.example.btcsedge.domain.model.Permission;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermissionDto {

    private Long id;
    private PermissionName name;
    private String description;

    public static PermissionDto from(Permission p) {
        return PermissionDto.builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .build();
    }
}

