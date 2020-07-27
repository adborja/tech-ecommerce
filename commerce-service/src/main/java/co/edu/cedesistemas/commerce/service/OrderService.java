package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository repository;
    private final EventPublisherService publisherService;

    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);
        order.calculateValue();
        log.info("order created: {}", order.getId());

        publisherService.publishOrderEvent(order);

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

    public Order getById(String id) {
        log.info("Retrieving Order by Id ", id);
        return repository.findById(id).orElse(null);
    }

    public List<OrderItem> getItemsByOrderId(String id) {
        log.info("Retrieving Items by Order Id ", id);
        Order orderFind = repository.findById(id).get();
        return orderFind.getItems();
    }

    @Override
    public void deleteOrder(final String id) {
        repository.deleteById(id);
    }
}
