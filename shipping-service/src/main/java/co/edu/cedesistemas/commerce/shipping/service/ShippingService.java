package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShippingRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentCancelledReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class ShippingService implements IShipmentService{

    private ShippingRepository repository;
    private EventPublisherService eventPublisherService;

    @Override
    public Shipment createShipment(Shipment shipment) {
        return repository.save(shipment);
    }

    @Override
    public Shipment getById(String id) {
        return repository.findById(id).get();
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return repository.findByTrackNumber(trackNumber);
    }


    @Override
    public Shipment updateDeliver(String id, ShipmentStatus status) {

        Shipment found = getById(id);
        if (found == null) {
            return null;
        }
        found.setStatus(status);
        return repository.save(found);

    }

    @Override
    public Shipment cancelShipment(String id, ShipmentCancelledReason reason) {

        Shipment found = getById(id);
        if (found == null) {
            return null;
        }
        found.setStatus(ShipmentStatus.CANCELLED);
        found.setShipmentCancelledReason(reason);
        eventPublisherService.publishShippingEvent(found);
        return repository.save(found);

    }
}
