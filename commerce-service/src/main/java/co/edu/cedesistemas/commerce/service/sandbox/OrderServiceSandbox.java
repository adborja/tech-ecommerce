package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.OrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {

    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        return order;
    }

    @Override
    public Order updateOrder(String id, Order order) {
        return null;
    }


    @Override
    public void deleteOrder(String id) {

    }

    @Override
    public Order getById(String id) {
        Order order = new Order();

        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());
        order.setId(id);
        order.setShippingAddressId("calle falsa 123");
        order.setStoreId(UUID.randomUUID().toString());
        order.setUserId("prueba 1");
        List<OrderItem> items = new ArrayList<>();
        OrderItem ordenItem = new OrderItem();
        ordenItem.setFinalPrice(10f);
        ordenItem.setProductId(UUID.randomUUID().toString());
        ordenItem.setQuantity(2);
        items.add(ordenItem);
        return order;
    }
}
