package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Address;
import co.edu.cedesistemas.commerce.shipping.model.Order;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.ShipmentStatus;
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
        shipment.setStatus(ShipmentStatus.CREATED);
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

    @Override
    public Shipment updateDelivery(String id) {
        return null;
    }

    @Override
    public Shipment updateStatusDeliver(String id, Shipment shipment) {
        return null;
    }

    @Override
    public Shipment cancelDelivery(String id, Shipment shipment) {
        return null;
    }

    private static Shipment createDummyShipment(final String id, final String trackNumber) {
        Shipment shipment = new Shipment();
        shipment.setId(id);
        shipment.setStatus(ShipmentStatus.IN_TRANSIT);
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
}
