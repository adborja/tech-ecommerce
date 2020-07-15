package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    List<OrderItem> getOrderItemsById(String id);
    Order getOrderById(String id);
}
