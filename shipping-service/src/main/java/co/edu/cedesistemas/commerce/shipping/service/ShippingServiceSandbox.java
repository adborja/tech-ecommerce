package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.model.Address;
import co.edu.cedesistemas.commerce.shipping.model.Order;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.repository.ShipmentRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class ShippingServiceSandbox implements IShipmentService {
    private final ShipmentRepository repository;
    private final EventPublisherService publisherService;

    @Override
    public Shipment createShipment(final Shipment shipment) {
        shipment.setId(UUID.randomUUID().toString());
        shipment.setStatus(Shipment.Status.CREATED);
        shipment.setCreatedAt(LocalDateTime.now());
        shipment.setTrackNumber(RandomStringUtils.randomNumeric(5));
        Shipment shipmentCreated =  repository.save(shipment);

        publisherService.publishShippingEvent(shipmentCreated);

        return shipmentCreated;
    }


    @Override
    public Shipment cancelDeliver(final String id) {
        Shipment shipment = getByTrackNumber(id);
        shipment.setStatus(Shipment.Status.CANCELLED);

        publisherService.publishShippingEvent(shipment);

        return repository.save(shipment);
    }


    @Override
    public Shipment deliver(final String id) {
        Shipment shipment = getByTrackNumber(id);
        shipment.setStatus(Shipment.Status.DELIVERED);

        publisherService.publishShippingEvent(shipment);

        return repository.save(shipment);
    }

    @Override
    public Shipment changeStatus(final String id, final Shipment.Status status) {
        Shipment shipment = getByTrackNumber(id);
        shipment.setStatus(status);

        publisherService.publishShippingEvent(shipment);

        return repository.save(shipment);
    }


    @Override
    public Shipment getById(final String id)
    {
        return getByTrackNumber( RandomStringUtils.randomNumeric(5));
    }

    @Override
    public Shipment getByTrackNumber(String trackNumber) {
        String id = UUID.randomUUID().toString();

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
        address.setCity("NEW YORK");
        address.setCountry("USA");
        address.setState("CAL");
        address.setZip("1234");

        order.setShippingAddress(address);

        shipment.setOrder(order);

        return shipment;
    }
}
