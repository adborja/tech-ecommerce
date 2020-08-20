package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ShippingService implements IShipmentService {

    private ShipmentRepository repository;
    private final EventPublisherService publisherService;

    @Override
    public Shipment createShipment(final Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setTrackNumber(RandomStringUtils.randomNumeric(5));
        Shipment result = repository.save(shipment);
        publisherService.publishShipmentEvent(publisherService.createEvent(shipment));
        return result ;
    }

    @Override
    public Shipment getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return repository.findByTrackNumber(trackNumber);
    }

    @Override
    public Shipment deliver(String id) {
        Shipment shipment = getById(id);
        shipment.setStatus(ShipmentStatus.DELIVERED);
        Shipment result = repository.save(shipment);
        publisherService.publishShipmentEvent(publisherService.createEvent(shipment));
        return result ;
    }

    @Override
    public Shipment cancel(Shipment.Cancel cancel) {
        Shipment shipment = getById(cancel.getId());
        shipment.setStatus(ShipmentStatus.CANCELLED);
        Shipment result = repository.save(shipment);
        publisherService.publishShipmentEvent(publisherService.createCancelEvent(shipment,
        cancel.getCancelStatus(), cancel.getDescription()));
        return result;
    }

    @Override
    public Shipment status(String id, ShipmentStatus status) {
        Shipment shipment = getById(id);
        shipment.setStatus(status);
        Shipment result = repository.save(shipment);
        return result;
    }
}
