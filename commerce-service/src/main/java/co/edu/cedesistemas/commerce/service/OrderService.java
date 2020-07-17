package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Profile("!"+SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository repository;

    public Order createOrder(final Order order){
        order.setId(UUID.randomUUID().toString());
        order.setStatus(Order.Status.CREATED);
        return repository.save(order);
    }

    public Order getById(final String id){
        return repository.findById(id).orElse(null);
    }

    public List<OrderItem> getItems(final String id){
        Order order = getById(id);
        if(order == null){
            return null;
        }
        return order.getItems();
    }
}
