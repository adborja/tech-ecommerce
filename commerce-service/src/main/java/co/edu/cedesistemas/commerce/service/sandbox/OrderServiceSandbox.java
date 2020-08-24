package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.OrderStatus;
import org.aspectj.weaver.ast.Or;
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
        order.setId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.CREATED);
        return order;
    }

    @Override
    public Order updateOrder(String id, Order order) {
        return null;
    }

    @Override
    public Order getById(String id) {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setId(id);
        order.setCreatedAt(LocalDateTime.now());
        order.setShippingAddressId("1");
        order.setStoreId("1");
        order.setUserId("jgaviria");
        return order;
    }

    @Override
    public void deleteOrder(String id) {

    }
}
