package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class OrderService implements IOrderService{
    private OrderRepository repository;

    public Order createOrder(Order order) {
        log.info("creating Order {}", order.getStatus());
        order.setId(UUID.randomUUID().toString());
        return repository.save(order);
    }

    public Order getById(String id) {
        log.info("Retrieving Order by Id ", id);
        return repository.findById(id).get();
    }

    public List<OrderItem> getItemsByOrderId(String id) {
        log.info("Retrieving Items by Order Id ", id);
        Order orderFind = repository.findById(id).get();
        return orderFind.getItems();
    }
}
