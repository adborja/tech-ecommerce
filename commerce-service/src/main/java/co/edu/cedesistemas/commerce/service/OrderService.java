package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.commerce.service.interfaces.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class OrderService implements IOrderService {

    private OrderRepository repository;
    public Order getById(String id){
        return repository.getById(id);
    }
    public Order createOrder(Order order){
        return repository.save(order);
    }
}
