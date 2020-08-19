package co.edu.cedesistemas.commerce.service;

import java.util.List;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;

public interface IOrderService {
	Order createOrder(Order order);
	List<OrderItem> getItemsByOrder(String orderId);
	Order getOrder(String id);
	void deleteOrder(String id);
}
