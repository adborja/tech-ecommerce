package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;

import java.util.List;

public interface IOrderService {
     List<OrderItem> getItemsByOrder(final String orderId);
     Order createOrder(final Order order);
     Order getOrder(final String id);

}
