package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.AddressRepository;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Order createOrder(final Order order) {
        order.setStatus(Order.Status.CREATED);
        return repository.save(order);
    }

    public Optional<Order> getOrder(final String id) {
           return repository.findById(id);
    }

}
