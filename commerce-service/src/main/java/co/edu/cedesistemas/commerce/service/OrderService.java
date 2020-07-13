package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class OrderService {

    private OrderRepository repository;

    public Order getById(String id){
        return repository.getById(id);
    }

    public Order createOrder(Order order){
        return repository.save(order);
    }
}
