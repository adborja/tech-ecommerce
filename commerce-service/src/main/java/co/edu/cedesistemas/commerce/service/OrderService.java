package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.commerce.repository.UserRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Profile("!"+ SpringProfile.SANDBOX)
public class OrderService implements IOrderService {
    private final OrderRepository repository;

    public Order createOrder(final Order order) {
        //order.setId(UUID.randomUUID().toString());
        return repository.save(order);
    }

    public Order getById(final String id) {
        return repository.findById(id).orElse(null);
    }
}
