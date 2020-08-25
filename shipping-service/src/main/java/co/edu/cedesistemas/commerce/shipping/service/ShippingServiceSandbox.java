package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Address;
import co.edu.cedesistemas.commerce.shipping.model.CancelReason;
import co.edu.cedesistemas.commerce.shipping.model.Order;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.model.Shipment.Status;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ShippingServiceSandbox implements IShipmentService {
    @Override
    public Shipment createShipment(final Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(Shipment.Status.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setTrackNumber(RandomStringUtils.randomNumeric(5));
        return shipment;
    }

    @Override
    public Shipment getById(final String id) {
        return createDummyShipment(id, RandomStringUtils.randomNumeric(5));
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        return createDummyShipment(UUID.randomUUID().toString(), trackNumber);
    }

    private static Shipment createDummyShipment(final String id, final String trackNumber) {
        Shipment shipment = new Shipment();
        shipment.setId(id);
        shipment.setStatus(Shipment.Status.IN_TRANSIT);
        shipment.setTrackNumber(trackNumber);
        shipment.setCreatedAt(LocalDateTime.now());

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setStoreId("tech-store-id");
        order.setUserId(UUID.randomUUID().toString());

        Order.OrderItem item = new Order.OrderItem();
        item.setId(UUID.randomUUID().toString());
        item.setQuantity(3);
        order.setItems(Collections.singletonList(item));

        Address address = new Address();
        address.setCity("Medellín");
        address.setCountry("CO");
        address.setState("ANT");
        address.setStreet1("Cr. 80 # 65 - 22");
        address.setZip("50030");

        order.setShippingAddress(address);

        shipment.setOrder(order);

        return shipment;
    }

	@Override
	public Shipment deliver(String id) {
		Shipment shipment = createDummyShipment(id, RandomStringUtils.randomNumeric(5));
		shipment.setStatus(Status.DELIVERED);
		return shipment;
		
	}

	@Override
	public Shipment cancel(String id, CancelReason reason) {
		Shipment shipment = createDummyShipment(id, RandomStringUtils.randomNumeric(5));
		shipment.setStatus(Status.CANCELLED);
		shipment.setCancelReason(reason);
		return shipment;
		
	}

	@Override
	public Shipment changeStatus(String id, String status) {
		Shipment shipment = createDummyShipment(id, RandomStringUtils.randomNumeric(5));
		
		Status newStatus = Status.valueOf(status) != null ? Status.valueOf(status) : shipment.getStatus();
		
		shipment.setStatus(newStatus);
		return shipment;
		
	}
}
