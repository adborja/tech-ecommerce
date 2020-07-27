package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository repository;

    @Override
    public Order createOrder(final Order order) {
        order.setStatus(Order.Status.CREATED);

        return repository.save(order);
    }

    @Override
    public Order getById(final String id) {
        return repository.findById(id).get();
    }

}
