package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShippingRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ShippingServiceImpl implements IShipmentService {

    private final ShippingRepository shippingRepository;
    private final EventPublisherService publisherService;

    @Override
    public Shipment createShipment(Shipment shipment) {
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setTrackNumber(UUID.randomUUID().toString());
        Shipment created = this.shippingRepository.save(shipment);
        this.publisherService.publishShipmentEvent(created);
        return created;
    }

    @Override
    public Shipment getById(String id) {
        Optional<Shipment> found = this.shippingRepository.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        Optional<Shipment> found = this.shippingRepository.findByTrackNumber(trackNumber);
        if (found.isPresent()) {
            return found.get();
        }
        return null;
    }

    @Override
    public Shipment deliverShipment(String id) {
        Optional<Shipment> found = this.shippingRepository.findById(id);
        if (found.isPresent()) {
            Shipment shipmentFound = found.get();
            shipmentFound.setStatus(ShipmentStatus.DELIVERED);
            Shipment delivered = this.shippingRepository.save(shipmentFound);
            this.publisherService.publishShipmentEvent(delivered);
            return delivered;
        }
        return null;
    }

    @Override
    public Shipment cancelShipment(String id) {
        Optional<Shipment> found = this.shippingRepository.findById(id);
        if (found.isPresent()) {
            Shipment shipmentFound = found.get();
            shipmentFound.setStatus(ShipmentStatus.CANCELLED);
            Shipment cancelled = this.shippingRepository.save(shipmentFound);
            this.publisherService.publishShipmentEvent(cancelled);
            return cancelled;
        }
        return null;
    }
}
