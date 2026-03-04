package com.example.btcsedge.repo;


import com.example.btcsedge.domain.enums.PermissionName;
import com.example.btcsedge.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Optional<Permission> findByName(PermissionName p);
    boolean existsByName(PermissionName name);
}
