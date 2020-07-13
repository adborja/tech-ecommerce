package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {
    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    @Override
    public Optional<Order> findById(String id) {

        Order order = new Order();
        order.setId(id);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(Order.Status.CREATED);

        OrderItem item1 = new OrderItem();
        item1.setFinalPrice(2000f);
        item1.setProductId("12233333");
        item1.setQuantity(23);
        OrderItem item2 = new OrderItem();
        item1.setFinalPrice(3000f);
        item1.setProductId("9899999");
        item1.setQuantity(29);
        List<OrderItem> orderItems = List.of(item1, item2);
        order.setItems(orderItems);
        order.setShippingAddressId("12278378478");
        order.setStoreId("84748494");
        order.setUserId("9090948894849");

        return Optional.of(order);
    }
}
