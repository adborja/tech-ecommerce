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

import static co.edu.cedesistemas.common.model.OrderStatus.ACCEPTED;
import static co.edu.cedesistemas.common.model.OrderStatus.CREATED;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {
    @Override
    public Order createOrder(Order order) {
        order.setStatus(CREATED);
        return order;
    }

    @Override
    public List<OrderItem> getOrderItemsById(String id) {
        List<OrderItem> items = new ArrayList<>();

        OrderItem item1 = new OrderItem();
        item1.setFinalPrice(1111.1F);
        item1.setProductId("producto1");
        item1.setQuantity(5);
        OrderItem item2 = new OrderItem();
        item1.setFinalPrice(1111.1F);
        item1.setProductId("producto2");
        item1.setQuantity(5);
        OrderItem item3 = new OrderItem();
        item1.setFinalPrice(1111.1F);
        item1.setProductId("producto3");
        item1.setQuantity(5);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        return items;
    }

    @Override
    public void deleteOrder(String id) {

    }

    @Override
    public Order updateOrder(String id, Order order) {
        return null;
    }

    @Override
    public Order getById(String id) {
        List<OrderItem> items = new ArrayList<>();

        OrderItem item1 = new OrderItem();
        item1.setFinalPrice(1111.1F);
        item1.setProductId("producto1");
        item1.setQuantity(5);
        items.add(item1);
        Order order = new Order();
       return Order.builder()
                .id(id)
                .items(items)
                .shippingAddressId("fake address")
                .status(ACCEPTED)
                .userId("fake user")
                .storeId("fake store id")
                .createdAt(LocalDateTime.now()).build();
    }
}
