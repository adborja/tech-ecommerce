package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.model.PaymentStatus;
import co.edu.cedesistemas.common.event.PaymentEvent;
import co.edu.cedesistemas.common.event.ShipmentEvent;
import co.edu.cedesistemas.common.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class ShipmentEventConsumerService {
    private final OrderService orderService;
    
	@RabbitListener(queues = "shipment.event.q")
	public void updateOrderStatus(Message in) {
		String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        ShipmentEvent event = ShipmentEvent.fromJSON(msg);
        
        OrderStatus orderStatus = resolveOrderStatus(event.getStatus());
        
        log.info("Invoking feign client of commerce service");
        
        Order _order = new Order();
        _order.setStatus(orderStatus);
        
        orderService.updateOrder(event.getOrderId(),_order);
        
	}
	
	private OrderStatus resolveOrderStatus(ShipmentEvent.Status status) {
		switch (status) {
		case CANCELLED:
			return OrderStatus.CANCELLED;
		case IN_TRANSIT:
			return OrderStatus.SHIPPED;
		case DELIVERED:
			return OrderStatus.DELIVERED;
		case CREATED:
			return OrderStatus.CONFIRMED;
		default:
			return null;
		}
	}
}
