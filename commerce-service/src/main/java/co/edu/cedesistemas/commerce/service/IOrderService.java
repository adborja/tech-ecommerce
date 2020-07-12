package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;

import java.util.Optional;

public interface IOrderService {
    Order createOrder(Order order);

    Optional<Order> findById(String id);
}
