package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    Order getById(String id);
    List<OrderItem> getItemsByOrderId(String id);
    Order updateOrder(String id, Order order);
    void deleteOrder(String id);
}
