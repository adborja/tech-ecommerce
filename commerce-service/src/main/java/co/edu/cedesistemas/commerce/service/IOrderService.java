package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;

import java.util.List;

public interface IOrderService {
    Order createOrder(Order order);
    Order getOrderById(String id);
     List<OrderItem> getOrderItemsById(String id) ;

    }
