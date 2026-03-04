package com.example.btcsedge.service;



import com.example.btcsedge.dto.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> getAll();
//    Permission getByName(PermissionName p);
}
