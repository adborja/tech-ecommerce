package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShippingRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Profile("!"+ SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class ShippingService implements IShipmentService{
    private final ShippingRepository repository;
    private final EventPublisherService publisherService;

    public Shipment createShipment(final Shipment shipment) {
        log.info("creating Shipment {}", shipment.getId());
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(ShipmentStatus.CREATED);
        Shipment result = repository.save(shipment);
        publisherService.publishShippingEvent(result);
        return result;
    }

    public Shipment getById(final String id){
        log.info("retrieving Shipment by Id {}", id);
        Shipment result = repository.findById(id).get();
        publisherService.publishShippingEvent(result);
        return result;
    }

    public Shipment getByTrackNumber(String trackNumber) {
        return null;
    }

    public Shipment updateStatusDeliver(String id, Shipment shipment) {
        Shipment shipmentFind = repository.findById(id).get();
        if(shipmentFind == null){
            log.warn("User not found: {}", id);
        }
        BeanUtils.copyProperties(shipment,shipmentFind, Utils.getNullPropertyNames(shipment));
        Shipment result = repository.save(shipmentFind);
        publisherService.publishShippingEvent(result);
        return result;
    }

    public Shipment updateDelivery(String id) {
        Shipment shipmentFind = repository.findById(id).get();
        if(shipmentFind == null){
            log.warn("User not found: {}", id);
        }
        shipmentFind.setStatus(ShipmentStatus.DELIVERED);
        Shipment result = repository.save(shipmentFind);
        publisherService.publishShippingEvent(result);
        return result;
    }

    public Shipment cancelDelivery(String id, Shipment shipment) {
        Shipment shipmentFind = repository.findById(id).get();
        if(shipmentFind == null){
            log.warn("User not found: {}", id);
        }
        BeanUtils.copyProperties(shipment,shipmentFind, Utils.getNullPropertyNames(shipment));
        shipmentFind.setStatus(ShipmentStatus.CANCELLED);
        Shipment result = repository.save(shipmentFind);
        publisherService.publishShippingEvent(result);
        return result;
    }
}
