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
public class OrderService implements IOrderService{

    private OrderRepository repository;

    public Order createOrder(Order order) {
        return repository.save(order);
    }

    public Order getById(String id) {
        return repository.findById(id).get();
    }

    public List<OrderItem> getOrderItems(String id){

        Optional<Order> order = repository.findById(id);

        List<OrderItem> orderItems = order.get().getItems();

        return orderItems;
    }
}
