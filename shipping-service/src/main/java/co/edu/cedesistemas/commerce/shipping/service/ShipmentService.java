package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.model.ShipmentCancelledReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ShipmentService implements IShipmentService{

    private final EventPublisherService publisherService;
    private final ShipmentRepository repository;

    @Override
    public Shipment createShipment(Shipment shipment) {
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(ShipmentStatus.CREATED);

        publisherService.publishShipmentEvent(shipment, ShipmentStatus.CREATED);

        return repository.save(shipment);
    }

    @Override
    public Shipment getById(String id) {
        return repository.getById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return repository.getByTrackNumber(trackNumber).orElse(null);
    }

    @Override
    public Shipment cancelShipment(String id, ShipmentCancelledReason reason, String description){
        Shipment found = getById(id);
        if(found != null){
            found.setStatus(ShipmentStatus.CANCELLED);
            found.setCancelledDescription(description);
            found.setCancelledReason(reason);

            publisherService.publishShipmentEvent(found, ShipmentStatus.CANCELLED);

            return repository.save(found);
        }else {
            return null;
        }
    }

    @Override
    public Shipment updateStatus(String id, ShipmentStatus status){
        Shipment found = getById(id);
        if(found != null){
            found.setStatus(status);
            return repository.save(found);
        }else {
            return null;
        }
    }

    @Override
    public Shipment deliveredShipment(String id) {
        Shipment found = getById(id);
        if(found != null){
            found.setStatus(ShipmentStatus.DELIVERED);
            publisherService.publishShipmentEvent(found, ShipmentStatus.DELIVERED);
            return repository.save(found);
        }else {
            return null;
        }
    }

}
