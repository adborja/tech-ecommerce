package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;

import java.util.Optional;

public interface IOrderService {
    Order createOrder(Order order);
    Order updateOrder(String id, Order order);
    Order getById(String id);
    void deleteOrder(String id);
}
