package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderService {
    private OrderRepository repository;

    public Order createOrder(Order order) {
        order.setStatus(Order.Status.CREATED);
        return repository.save(order);
    }

    public List<OrderItem> getOrderItemsById(String id) {
        Optional<Order> optional = repository.findById(id);
        if(optional.isEmpty()) return Collections.emptyList();
        else return optional.get().getItems();
    }

    public Order getOrderById(String id) {
        Order order = repository.findById(id).get();
        return order;
    }
}
