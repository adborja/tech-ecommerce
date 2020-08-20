package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Address;
import co.edu.cedesistemas.commerce.shipping.model.Cancel;
import co.edu.cedesistemas.commerce.shipping.model.Order;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.event.ShipmentEvent;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ShippingService implements IShipmentService {

    private final ShipmentRepository repository;
    private final EventPublisherService eventPublisherService;

    @Override
    public Shipment createShipment(final Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(Shipment.Status.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setTrackNumber(RandomStringUtils.randomNumeric(5));

        repository.save(shipment);

        eventPublisherService.publishRegistrationEvent(shipment, ShipmentEvent.Status.CREATED);

        return shipment;
    }

    @Override
    public Shipment getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return repository.findByTrackNumber(trackNumber).orElse(null);
    }

    @Override
    public Shipment updateStatus(String id) {

        Shipment shipment = repository.findById(id).orElse(null);
        if(shipment != null){
            shipment.setStatus(Shipment.Status.CANCELLED);
            repository.save(shipment);
            eventPublisherService.publishRegistrationEvent(shipment, ShipmentEvent.Status.CANCELLED);
        }
        return null;
    }

    @Override
    public Shipment cancelShipment(String id, Cancel cancel) {
        return null;
    }

}
