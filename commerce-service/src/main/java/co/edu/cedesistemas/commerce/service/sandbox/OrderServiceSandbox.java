package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
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
        order.setStatus(Order.Status.CREATED);
        return order;
    }

    @Override
    public Order getById(String id) {
        Order order = new Order();
        order.setStatus(Order.Status.CREATED);
        order.setId(id);
        order.setCreatedAt(LocalDateTime.now());
        order.setShippingAddressId("1");
        order.setStoreId("1");
        order.setUserId("jgaviria");
        return order;
    }

    @Override
    public List<OrderItem> getItems(String id) {
        List<OrderItem> list = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId("1");
        orderItem1.setFinalPrice(500f);
        orderItem1.setQuantity(10);
        list.add(orderItem1);
        return list;
    }
}
