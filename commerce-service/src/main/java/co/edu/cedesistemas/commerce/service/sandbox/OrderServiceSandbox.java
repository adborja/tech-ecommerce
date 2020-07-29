package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.OrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public Order getById(String id) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(OrderStatus.ACCEPTED);
        order.setShippingAddressId("Address example to shipping");
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }

    @Override
    public List<OrderItem> getItemsByOrderId(String id) {
        //create products
        Product product = new Product();
        product.setDescription("desc 1");
        product.setId(UUID.randomUUID().toString());
        product.setName("prod 1");

        Product product2 = new Product();
        product.setDescription("desc 2");
        product.setId(UUID.randomUUID().toString());
        product.setName("prod 2");

        //create items
        OrderItem item1 = new OrderItem();
        item1.setFinalPrice(new Float(234.7));
        item1.setProductId(UUID.randomUUID().toString());
        item1.setQuantity(2);

        OrderItem item2 = new OrderItem();
        item1.setFinalPrice(new Float(34.45));
        item1.setProductId(UUID.randomUUID().toString());
        item1.setQuantity(3);

        List<OrderItem> orderItems = new ArrayList();
        orderItems.add(item1);
        orderItems.add(item2);

        return Arrays.asList(item1,item2);
    }

    @Override
    public Order updateOrder(String id, Order order) {
        return null;
    }

    @Override
    public void deleteOrder(String id) {

    }
}
