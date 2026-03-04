package com.example.btcsedge.service.impl;

import com.example.btcsedge.dto.PermissionDto;
import com.example.btcsedge.repo.PermissionRepository;
import com.example.btcsedge.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public List<PermissionDto> getAll() {
        return permissionRepository.findAll().stream()
                .map(PermissionDto::from)
                .collect(Collectors.toList());
    }
}
