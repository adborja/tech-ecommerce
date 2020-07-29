package co.edu.cedesistemas.commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.repository.OrderRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
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
	public Order updateOrder(String id, Order order) {
		Order found = repository.findById(id).orElse(null);
		
		if(found == null) {
			log.error("order {} not found",id);
			return null;
		}
		
		BeanUtils.copyProperties(order, found, Utils.getNullPropertyNames(order));
		
		return repository.save(found);
	}
}
