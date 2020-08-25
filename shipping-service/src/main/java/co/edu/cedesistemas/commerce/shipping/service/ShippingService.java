package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.CanceledDelivery;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile(SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ShippingService implements IShipmentService {
    private final EventPublisherService publisherService;

    private ShipmentRepository repository;

    @Override
    public Shipment createShipment(final Shipment shipment) {
        publisherService.publishShipmentEvent(shipment, ShipmentStatus.CREATED);
        return repository.save(shipment);


    }

    @Override
    public Shipment getById(final String id) {
        Shipment shipment = repository.findById(id).get();
        publisherService.publishShipmentEvent(shipment,null);
        return shipment;
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        Shipment shipment = repository.findByTrackNumber(trackNumber);
        publisherService.publishShipmentEvent(shipment, null);
        return shipment;
    }

    @Override
    public Shipment deliver(final String id) {
        Shipment found  = getById(id);
        if(found  == null) return null;
        found .setStatus(ShipmentStatus.DELIVERED);
        found .setCanceledDelivery(null);
        publisherService.publishShipmentEvent(found , ShipmentStatus.DELIVERED);
        return repository.save(found );
    }


    @Override
    public Shipment update(String id, ShipmentStatus status) {
        Shipment shipmentUpdated = repository.findById(id).orElse(null);
        if (shipmentUpdated == null) return null;
        else {
            shipmentUpdated.setStatus(status);
            shipmentUpdated.setCanceledDelivery(null);
            publisherService.publishShipmentEvent(shipmentUpdated, status);
            repository.save(shipmentUpdated);
            return shipmentUpdated;
        }

    }

    @Override
    public Shipment cancelShipment(String id, CanceledDelivery canceledDelivery) {
        Shipment shipmentCancel =   repository.findById(id).orElse(null);
        shipmentCancel.setStatus(ShipmentStatus.CANCELLED);
        shipmentCancel.setCanceledDelivery(canceledDelivery);
        publisherService.publishShipmentEvent(shipmentCancel, ShipmentStatus.CANCELLED);
        return repository.save(shipmentCancel);
    }
}

