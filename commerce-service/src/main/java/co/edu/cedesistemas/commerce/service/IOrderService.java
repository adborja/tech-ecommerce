package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;

public interface IOrderService {
    Order createOrder(Order order);
    Order getOrderById(String orderId);
}
