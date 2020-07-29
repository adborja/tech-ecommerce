package co.edu.cedesistemas.commerce.service;


import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class OrderService implements IOrderService{

    private OrderRepository repository;
    private final EventPublisherService publisherService;

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order updateOrder(final String id, final Order order) {
        Order found = getById(id);
        if (found == null) {
            log.warn("order not found: {}", id);
            return null;
        }
        BeanUtils.copyProperties(order, found, Utils.getNullPropertyNames(order));

        publisherService.publishOrderEvent(found);

        return repository.save(found);
    }

    @Override
    public Order getById(String id) {
        return repository.findById(id).get();
    }

    public List<OrderItem> getOrderItems(String id){

        Optional<Order> order = repository.findById(id);

        List<OrderItem> orderItems = order.get().getItems();

        return orderItems;
    }
}
