package com.example.btcsedge.ingestion;



import com.example.btcsedge.domain.model.*;
import com.example.btcsedge.domain.enums.Direction;
import com.example.btcsedge.repo.AnprEventRepo;
import com.example.btcsedge.repo.VehicleEventRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AnprIngestionService {

    private final VehicleEventRepo vehicleEventRepo;
    private final AnprEventRepo anprEventRepo;

    public AnprIngestionService(VehicleEventRepo vehicleEventRepo, AnprEventRepo anprEventRepo) {
        this.vehicleEventRepo = vehicleEventRepo;
        this.anprEventRepo = anprEventRepo;
    }

    public record AnprCmd(short laneNo, Direction direction, String cameraId, String plateNumber,
                          Double confidence, String imagePath) {}

    @Transactional
    public UUID ingest(AnprCmd cmd) {
        UUID correlationId = UUID.randomUUID();

        VehicleEvent ve = new VehicleEvent(correlationId, cmd.laneNo(), cmd.direction());
        ve.markAnprDone(cmd.plateNumber());
        ve = vehicleEventRepo.save(ve);

        anprEventRepo.save(new AnprEvent(
                ve, cmd.cameraId(), cmd.plateNumber(), cmd.confidence(), cmd.imagePath()
        ));

        return correlationId; // keyin weight shu ID bilan keladi
    }
}
