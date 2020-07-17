package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository repository;

    public Order createOrder(final Order order) {
        order.setStatus(Order.Status.CREATED);

        return repository.save(order);
    }

    public Order getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    public List<OrderItem> getOrderItems(String id){
        Optional<Order> order = repository.findById(id);
        List<OrderItem> orderItems = order.get().getItems();
        return orderItems;
    }
}
