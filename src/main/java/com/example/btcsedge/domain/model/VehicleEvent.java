package com.example.btcsedge.domain.model;

import com.example.btcsedge.domain.enums.Direction;
import com.example.btcsedge.domain.enums.VehicleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "vehicle_event")
public class VehicleEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correlation_id", nullable = false, unique = true)
    private UUID correlationId;

    @Column(name = "lane_no", nullable = false)
    private short laneNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Direction direction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleStatus status;

    @Column(name = "plate_number", length = 20)
    private String plateNumber;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected VehicleEvent() {}

    public VehicleEvent(UUID correlationId, short laneNo, Direction direction) {
        this.correlationId = Objects.requireNonNull(correlationId);
        this.laneNo = laneNo;
        this.direction = Objects.requireNonNull(direction);
        this.status = VehicleStatus.CREATED;
    }

    public void markAnprDone(String plate) {
        this.plateNumber = plate;
        this.status = VehicleStatus.ANPR_DONE;
    }

}
