package co.edu.cedesistemas.commerce.service;


import co.edu.cedesistemas.commerce.model.Order;
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

    private OrderService orderService;

    @RabbitListener(queues = "shipment.event.q")
    public void listenShipmentEvent(Message in) {
        String message = new String(in.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        ShipmentEvent shipmentEvent = ShipmentEvent.fromJSON(message);

        if (shipmentEvent == null) {
            log.error("could not read shipment event");
            return;
        }

        String orderId = shipmentEvent.getOrderID();
        Order _order = new Order();

        switch (shipmentEvent.getStatus()){
            case DELIVERED: _order.setStatus(OrderStatus.DELIVERED);break;
            case CREATED: _order.setStatus(OrderStatus.SHIPPED);break;
            case CANCELLED:_order.setStatus(OrderStatus.CANCELLED);break;
            case IN_TRANSIT:_order.setStatus(OrderStatus.SHIPPED);break;
        }

        Order order = orderService.updateOrder(orderId, _order);
        log.info("order updated - id: {}, status: {}", order.getId(), order.getStatus());

    }
}
