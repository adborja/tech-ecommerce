package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.interfaces.IOrderService;
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
    public Order getById(String id) {
        Order order = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setFinalPrice(123123F);
        orderItem.setProductId(UUID.randomUUID().toString());
        orderItem.setQuantity(2);
        orderItems.add(orderItem);
        order.setId(id);
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(orderItems);
        order.setShippingAddressId("Fake Street 123124");
        order.setStatus(Order.Status.DELIVERED);
        order.setStoreId(UUID.randomUUID().toString());
        order.setUserId(UUID.randomUUID().toString());
        return order;
    }

    @Override
    public Order createOrder(Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setFinalPrice(987223F);
        orderItem.setProductId(UUID.randomUUID().toString());
        orderItem.setQuantity(85);
        orderItems.add(orderItem);
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setItems(orderItems);
        order.setShippingAddressId("Fake Street 8979832");
        order.setStatus(Order.Status.CREATED);
        order.setStoreId(UUID.randomUUID().toString());
        order.setUserId(UUID.randomUUID().toString());
        return order;
    }
}
