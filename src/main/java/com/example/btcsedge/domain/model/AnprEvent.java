package com.example.btcsedge.domain.model;


import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "anpr_event")
public class AnprEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_event_id", nullable = false)
    private VehicleEvent vehicleEvent;

    @Column(name = "camera_id", nullable = false, length = 50)
    private String cameraId;

    @Column(name = "plate_number", length = 20)
    private String plateNumber;

    @Column(name = "confidence")
    private Double confidence;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    protected AnprEvent() {}

    public AnprEvent(VehicleEvent vehicleEvent, String cameraId, String plateNumber, Double confidence, String imagePath) {
        this.vehicleEvent = vehicleEvent;
        this.cameraId = cameraId;
        this.plateNumber = plateNumber;
        this.confidence = confidence;
        this.imagePath = imagePath;
    }
}
