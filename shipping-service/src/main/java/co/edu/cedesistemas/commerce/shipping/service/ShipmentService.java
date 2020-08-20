package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Cancellation;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentCancelReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@AllArgsConstructor
@Profile("!" + SpringProfile.SANDBOX)
public class ShipmentService implements IShipmentService {

    private final ShipmentRepository repository;
    private final EventPublisher eventPublisher;

    @Override
    public Shipment deliver(String id) {
        Shipment shipment = getById(id);
        shipment.setStatus(ShipmentStatus.DELIVERED);
        repository.save(shipment);
        eventPublisher.publishShipmentEvent(shipment);
        return shipment;
    }

    @Override
    public Shipment send(String id, String trackNumber) {
        Shipment shipment = getById(id);
        shipment.setTrackNumber(trackNumber);
        shipment.setStatus(ShipmentStatus.IN_TRANSIT);
        repository.save(shipment);
        eventPublisher.publishShipmentEvent(shipment);
        return shipment;
    }

    @Override
    public Shipment cancel(String id) {
        Shipment shipment = getById(id);
        shipment.setStatus(ShipmentStatus.CANCELLED);
        shipment.setCancellation(Cancellation.builder()
                .reason(ShipmentCancelReason.CUSTOMER_REJECTED)
                .description("Cancelada por el usuario").build());
        repository.save(shipment);
        eventPublisher.publishShipmentEvent(shipment);
        return shipment;
    }

    @Override
    public Shipment createShipment(Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        if(shipment.getOrder().getUserId().equals("")){
            shipment.setStatus(ShipmentStatus.CANCELLED);
            shipment.setCancellation(Cancellation.builder()
                    .reason(ShipmentCancelReason.CUSTOMER_NOT_FOUND)
                    .description("Usuario no encontrado").build());
        }
        if(shipment.getOrder().getShippingAddress().getStreet1().equals("")){
            shipment.setStatus(ShipmentStatus.CANCELLED);
            shipment.setCancellation(Cancellation.builder()
                    .reason(ShipmentCancelReason.ADDRESS_NOT_FOUND)
                    .description("Direccion no encontrada").build());
        }
        repository.save(shipment);
        eventPublisher.publishShipmentEvent(shipment);
        return shipment;
    }

    @Override
    public Shipment getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return repository.findByTrackNumber(trackNumber);
    }
}
