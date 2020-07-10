package co.edu.cedesistemas.commerce.service;


import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository repository;

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Optional<Order> getById(String id) {
        return repository.findById(id);
    }

    public List<OrderItem> getOrderItems(String id){

        Optional<Order> order = repository.findById(id);

        List<OrderItem> orderItems = order.get().getItems();

        return orderItems;
    }
}
