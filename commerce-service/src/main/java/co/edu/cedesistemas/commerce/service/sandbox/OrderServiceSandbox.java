package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {
    @Override
    public Order createOrder(Order order) {
        order.setStatus(Order.Status.CREATED);
        return order;
    }

    @Override
    public List<OrderItem> getOrderItemsById(String id) {
        List<OrderItem> items = new ArrayList<>();

        OrderItem item1 = OrderItem.builder()
                .finalPrice(1111.1F)
                .productId("producto1")
                .quantity(5).build();
        OrderItem item2 = OrderItem.builder()
                .finalPrice(2222.2F)
                .productId("producto2")
                .quantity(3).build();
        OrderItem item3 = OrderItem.builder()
                .finalPrice(3333.3F)
                .productId("producto3")
                .quantity(10).build();
        items.add(item1);
        items.add(item2);
        items.add(item3);
        return items;
    }

    @Override
    public Order getOrderById(String id) {
        List<OrderItem> items = new ArrayList<>();

        OrderItem item1 = OrderItem.builder()
                .finalPrice(1111.1F)
                .productId("producto1")
                .quantity(5).build();
        items.add(item1);
       return Order.builder()
                .id(id)
                .items(items)
                .shippingAddressId("fake address")
                .status(Order.Status.ACCEPTED)
                .userId("fake user")
                .storeId("fake store id")
                .createdAt(LocalDateTime.now()).build();
    }
}
