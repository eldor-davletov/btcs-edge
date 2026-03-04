package com.example.btcsedge.repo;


import com.example.btcsedge.domain.enums.RoleName;
import com.example.btcsedge.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(RoleName name);
    boolean existsByName(RoleName name);
}
