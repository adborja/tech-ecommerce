package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface IOrderService {

    Order createOrder(Order order);

    Order getById(String id) throws Exception;

    List<OrderItem> getOrderItems(String id);
}
