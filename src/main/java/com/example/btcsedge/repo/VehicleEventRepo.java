package com.example.btcsedge.repo;



import com.example.btcsedge.domain.model.VehicleEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VehicleEventRepo extends JpaRepository<VehicleEvent, Long> {
    Optional<VehicleEvent> findByCorrelationId(UUID correlationId);
}
