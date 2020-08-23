package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.Repository.ShippingRepository;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class ShippingService implements IShipmentService {

    private ShippingRepository repository;
    private EventPublisherService eventPublisherService;


    @Override
    public Shipment createShipment(Shipment shipment) {
        shipment.setStatus(ShipmentStatus.CREATED);
        return repository.save(shipment);
    }

    @Override
    public Shipment getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return repository.findById(trackNumber).orElse(null);
    }

    @Override
    public Shipment updateDeliver(String id, Shipment shipment) {
        Shipment found = getById(id);
        if (found != null) {
            found.setStatus(ShipmentStatus.DELIVERED);
            found.setOrder(shipment.getOrder());
            found.setCreatedAt(shipment.getCreatedAt());
            log.info("update shipment {}", shipment);
            return repository.save(found);
        } return null;

    }

    @Override
    public Shipment cancelShipment(String id, Shipment shipment) {
        Shipment found = getById(id);
        if (found == null) {
            return null;
        }
        found.setStatus(ShipmentStatus.CANCELLED);
        found.setShipmentCancelled(shipment.getShipmentCancelled());
        log.info("update shipment {}", shipment);
        eventPublisherService.publishShippingEvent(found);
        return repository.save(found);

    }

}
