package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.model.ShipmentCancelReason;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.event.ShippingEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class ShippingService implements IShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final EventPublisherService publisherService;

    @Override
    public Shipment createShipment(Shipment shipment) {
        shipment.setStatus(Shipment.Status.CREATED);
        shipment.setTrackNumber(UUID.randomUUID().toString());
        log.info("Shipping created {}", shipment.getId());
        Shipment shipmentSaved = shipmentRepository.save(shipment);
        publisherService.publishUpdateStatusEvent(shipmentSaved, ShippingEvent.Status.CREATED);

        return shipmentRepository.save(shipment);
    }

    @Override
    public Shipment getById(String id) {

        return shipmentRepository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return null;
    }

    @Override
    public Shipment deliverShipment(String id) {
        Shipment shipmentFound = getById(id);
        if(shipmentFound != null){
            log.warn("Shipment ot found {}", id);
            shipmentFound.setStatus(Shipment.Status.DELIVERED);
            publisherService.publishUpdateStatusEvent(shipmentFound, ShippingEvent.Status.DELIVERED);
            shipmentRepository.save(shipmentFound);
        }
        return shipmentFound;
    }

    @Override
    public Shipment cancelShipment(String id, ShipmentCancelReason reason) {
        log.info("Cancelling Shipment {}", id);
        Shipment shipmentFound = getById(id);
        if(shipmentFound != null){
            shipmentFound.setStatus(Shipment.Status.CANCELED);
            shipmentFound.setCancelReason(reason);
            publisherService.publishUpdateStatusEvent(shipmentFound, ShippingEvent.Status.CANCELED);
            shipmentRepository.save(shipmentFound);
        }
        return shipmentFound;
    }


    @Override
    public Shipment updateShipmentStatus(String id, Shipment.Status status) {
        Shipment shipmentFound = getById(id);
        if(shipmentFound != null){
            shipmentFound.setStatus(status);
            shipmentRepository.save(shipmentFound);
        }
        return shipmentFound;
    }
}
