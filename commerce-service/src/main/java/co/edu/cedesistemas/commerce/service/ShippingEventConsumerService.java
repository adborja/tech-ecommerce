package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.event.ShippingEvent;
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
public class ShippingEventConsumerService {
    private final OrderService orderService;

    @RabbitListener(queues = "shipment.event.q")
    public void listenShippingEvent(Message messageIn){
        String message = new String(messageIn.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        ShippingEvent shippingEvent = ShippingEvent.fromJSON(message);

        if(shippingEvent == null){
            log.error("could not read shipping event");
            return;
        }

        String orderId = shippingEvent.getId();
        Order orderFound = setOrderStatusFromShippingEvent(shippingEvent);
        orderService.updateOrder(orderId, orderFound);
        log.info("order updated - id: {}, status: {}", orderFound.getId(), orderFound.getStatus());

    }

    private Order setOrderStatusFromShippingEvent(ShippingEvent shippingEvent){
        Order order = new Order();
        switch (shippingEvent.getStatus()){
            case CREATED:
                order.setStatus(OrderStatus.CREATED);
                break;
            case CANCELED:
                order.setStatus(OrderStatus.CANCELLED);
                break;
            case DELIVERED:
                order.setStatus(OrderStatus.DELIVERED);
                break;
            default:
                order.setStatus(OrderStatus.SHIPPED);
                break;
        }
        return order;
    }
}
