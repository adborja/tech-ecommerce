package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.CancelReason;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.model.Shipment.Status;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.event.ShipmentEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ShippingService implements IShipmentService {
	
	private ShipmentRepository repository;
	private EventPublisherService publishService;
	
    @Override
    public Shipment createShipment(final Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(Shipment.Status.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        
        
        publishService.publishShippingEvent(shipment, ShipmentEvent.Status.CREATED);
        log.info("creating shipment {}", shipment.getId());
        return repository.save(shipment);
    }

    @Override
    public Shipment getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(final String trackNumber) {
        return repository.findByTrackNumber(trackNumber).orElse(null);
    }

	@Override
	public Shipment deliver(final String id) {
		Shipment found = getById(id);
		if(found == null) return null;
		found.setStatus(Status.DELIVERED);
		found.setCancelReason(null);
		publishService.publishShippingEvent(found, ShipmentEvent.Status.DELIVERED);
		log.info("status of shipment {}: {}",found.getId(),found.getStatus().name());
		return repository.save(found);
	}

	@Override
	public Shipment cancel(final String id, final CancelReason reason) {
		Shipment found = getById(id);
		if(found == null) return null;
		found.setStatus(Status.CANCELLED);
		found.setCancelReason(reason);
		publishService.publishShippingEvent(found, ShipmentEvent.Status.CANCELLED);
		log.info("status of shipment {}: {}",found.getId(),found.getStatus().name());
		return repository.save(found);
		
	}

	@Override
	public Shipment changeStatus(final String id, final String status) {
		Shipment found = getById(id);
		if(found == null) return null;
		
		Status newStatus = Status.valueOf(status);
		newStatus = newStatus != null ? newStatus : found.getStatus();
		found.setStatus(newStatus);
		found.setCancelReason(null);
		publishService.publishShippingEvent(found, ShipmentEvent.Status.valueOf(found.getStatus().name()));
		log.info("status of shipment {}: {}",found.getId(),found.getStatus().name());
		return repository.save(found);
	}


	

}
