package com.example.btcsedge.domain.model;



import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "weight_event")
public class WeightEvent {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_event_id", nullable = false)
    private VehicleEvent vehicleEvent;

    @Column(name = "scale_id", nullable = false, length = 50)
    private String scaleId;

    @Column(name = "weight_kg", nullable = false)
    private Integer weightKg;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected WeightEvent() {}

    public WeightEvent(VehicleEvent vehicleEvent, String scaleId, int weightKg) {
        this.vehicleEvent = vehicleEvent;
        this.scaleId = scaleId;
        this.weightKg = weightKg;
    }
}
