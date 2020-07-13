package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(String id) {
        return this.orderRepository.findById(id);
    }
}
