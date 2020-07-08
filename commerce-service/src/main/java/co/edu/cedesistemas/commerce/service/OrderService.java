package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository repository;

    public Order createOrder(final Order order) {
        order.setStatus(Order.Status.CREATED);

        return repository.save(order);
    }

    public Order getById(final String id) {
        return repository.findById(id).get();
    }

}
