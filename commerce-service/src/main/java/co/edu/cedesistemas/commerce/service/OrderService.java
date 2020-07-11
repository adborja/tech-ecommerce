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
    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order){
        order.setStatus(Order.Status.CREATED);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String orderId){
        return orderRepository.findById(orderId)
                .orElse(null);
    }

}
