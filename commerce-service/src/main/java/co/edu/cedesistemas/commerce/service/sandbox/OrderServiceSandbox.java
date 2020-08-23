package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class OrderServiceSandbox implements  IOrderService{
    private final OrderRepository repository;

    @Override
    public List<OrderItem> getItemsByOrder(final String orderId)
    {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setDescription("Leche colanta");
        product.setName("Leche");

        Product product2 = new Product();
        product2.setId(UUID.randomUUID().toString());
        product2.setDescription("Queso colanta");
        product2.setName("Queso");

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId(UUID.randomUUID().toString());
        orderItem1.setFinalPrice((float) 2500);
        orderItem1.setQuantity(10);


        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductId(UUID.randomUUID().toString());
        orderItem2.setFinalPrice((float) 4800);
        orderItem2.setQuantity(10);

        return Arrays.asList(orderItem1,orderItem2);

    }
    @Override
    public Order createOrder(final Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        //order.setStatus(Order.Status.CREATED);
        repository.save(order);
        return order;
    }

    @Override
    public Order getOrder(final String id)
    {
        String idNuevo=null;
        Order order = new Order();
        User user = new User();

        idNuevo=UUID.randomUUID().toString();
        user.setId(idNuevo);
        user.setName("Jaime");
        user.setLastName("Barrera");

        order.setId(id);
        order.setCreatedAt(LocalDateTime.now());
        order.setUserId(idNuevo);

        //Optional<Order> order1 = repository.findById(id);
        //return repository.findById(id);
    }

    @Override
    public void deleteOrder(String id) {
        repository.deleteById(id);
    }

}
