package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {
    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(Order.Status.CREATED);
        return order;
    }

    @Override
    public Order getById(String id) {
        List<OrderItem> orderItemList = Arrays.asList(
                OrderItem.builder()
                        .productId(UUID.randomUUID().toString())
                        .quantity(2)
                        .finalPrice(10000F)
                        .build(),

                OrderItem.builder()
                        .productId(UUID.randomUUID().toString())
                        .quantity(4)
                        .finalPrice(550000F)
                        .build());


        return Order.builder()
                .id(id)
                .createdAt(LocalDateTime.now().minus(40, ChronoUnit.MINUTES))
                .shippingAddressId(UUID.randomUUID().toString())
                .status(Order.Status.CREATED)
                .storeId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .items(orderItemList)
                .build();
    }
}
