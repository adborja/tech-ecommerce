package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService implements IOrderService{
    private OrderRepository repository;

    public Order createOrder(Order order) {
        order.setStatus(Order.Status.CREATED);
        return repository.save(order);
    }

    public List<OrderItem> getOrderItemsById(String id) {
        Order order = repository.findById(id).orElse(null);
         return order.getItems();
    }

    public Order getOrderById(String id) {
        Order order = repository.findById(id).get();
        return order;
    }
}