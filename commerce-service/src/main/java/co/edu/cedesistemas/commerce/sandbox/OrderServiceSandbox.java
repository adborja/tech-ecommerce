package co.edu.cedesistemas.commerce.sandbox;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Profile;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Order;
//import co.edu.cedesistemas.commerce.model.Order.Status;
import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;

@Profile(SpringProfile.SANDBOX)
public class OrderServiceSandbox implements IOrderService{

	@Override
	public Order createOrder(Order order) {
		order.setId(UUID.randomUUID().toString());
		order.setCreatedAt(LocalDateTime.now());
		return order;
	}

	@Override
	public List<OrderItem> getItemsByOrder(String orderId) {
		
		Product p1 = new Product();
		p1.setName("producto1");
		p1.setId(UUID.randomUUID().toString());
		p1.setDescription("descripcion1");
		
		Product p2 = new Product();
		p2.setName("producto2");
		p2.setId(UUID.randomUUID().toString());
		p2.setDescription("descripcion2");
		
		OrderItem o1 = new OrderItem();
		o1.setFinalPrice(123.53F);
		o1.setProductId(p1.getId());
		o1.setQuantity(2);
		
		OrderItem o2 = new OrderItem();
		o2.setFinalPrice(150.53F);
		o2.setProductId(p2.getId());
		o2.setQuantity(1);
		
		return Arrays.asList(o1,o2);
	}

	@Override
	public Order getOrder(String id) {
		Address a = new Address();
		a.setId(UUID.randomUUID().toString());
		
		Order o = new Order();
		o.setItems(getItemsByOrder(id));
		o.setId(UUID.randomUUID().toString());
		o.setStatus(OrderStatus.CREATED);
		o.setShippingAddressId(a.getId());

		return o;
	}

}
