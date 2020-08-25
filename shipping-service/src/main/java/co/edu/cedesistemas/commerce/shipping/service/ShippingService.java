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
        
        
        publishService.publishShippingEvent(shipment);
        log.info("creating shipment {}", shipment.getId());
        return repository.save(shipment);
    }

    @Override
    public Shipment getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Shipment getByTrackNumber(final String trackNumber) {
        return repository.findById(trackNumber).orElse(null);
    }

	@Override
	public Shipment deliver(final String id) {
		Shipment found = getById(id);
		if(found == null) return null;
		found.setStatus(Status.DELIVERED);
		publishService.publishShippingEvent(found);
		log.info("status of shipment {}: {}",found.getId(),found.getStatus().name());
		return repository.save(found);
	}

	@Override
	public Shipment cancelDeliver(final String id, final Shipment.Motivo reason) {
		Shipment found = getById(id);
		if(found == null) return null;
		found.setStatus(Status.CANCELLED);
		found.setMotivoDevolucion(reason.toString());
		publishService.publishShippingEvent(found);
		log.info("status of shipment {}: {}",found.getId(),found.getStatus().name());
		return repository.save(found);
		
	}

	@Override
	public  Shipment changeStatus(final String id, final Shipment.Status status) {
		Shipment found = getById(id);
		if(found == null) return null;
		
		Status newStatus = Status.valueOf(status.toString());
		newStatus = newStatus != null ? newStatus : found.getStatus();
		found.setStatus(newStatus);
		publishService.publishShippingEvent(found);
		log.info("status of shipment {}: {}",found.getId(),found.getStatus().name());
		return repository.save(found);
	}


	

}
