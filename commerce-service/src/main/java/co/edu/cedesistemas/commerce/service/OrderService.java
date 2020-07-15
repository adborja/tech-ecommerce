package co.edu.cedesistemas.commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService implements IOrderService{
	
	private final OrderRepository repository; 
	@Override
	public List<OrderItem> getItemsByOrder(final String orderId){
		Optional<Order> order = repository.findById(orderId);
		
		return order.isPresent() ? order.get().getItems() : null;
	}
	@Override
	public Order createOrder(final Order order) {
		return repository.save(order);
	}
	@Override
	public Order getOrder(final String id) {
		Optional<Order> order = repository.findById(id);
		return order.isPresent() ? order.get() : null;
	}
}
