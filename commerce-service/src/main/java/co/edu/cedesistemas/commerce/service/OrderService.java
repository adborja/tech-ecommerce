package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public Optional<Order> findById(String id) {
        return this.orderRepository.findById(id);
    }
}
