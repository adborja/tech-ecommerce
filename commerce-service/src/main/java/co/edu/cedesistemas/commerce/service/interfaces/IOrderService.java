package co.edu.cedesistemas.commerce.service.interfaces;

import co.edu.cedesistemas.commerce.model.Order;

public interface IOrderService {
    Order getById(String id);
    Order createOrder(Order order);
}
