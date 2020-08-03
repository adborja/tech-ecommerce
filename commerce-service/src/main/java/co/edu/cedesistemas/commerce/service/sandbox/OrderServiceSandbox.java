package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.OrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        order.setStatus(OrderStatus.CREATED);
        return order;
    }

    @Override
    public Order updateOrder(String id, Order order) {
        return null;
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
                .status(OrderStatus.CREATED)
                .storeId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .items(orderItemList)
                .build();
    }

    @Override
    public void deleteOrder(String id) {

    }
}
