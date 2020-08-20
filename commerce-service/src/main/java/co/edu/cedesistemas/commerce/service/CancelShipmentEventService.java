package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.event.PaymentEvent;
import co.edu.cedesistemas.common.event.ShipmentEvent;
import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.model.ShipmentCancelReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class CancelShipmentEventService {
    private OrderService orderService;

    @RabbitListener(queues = "shipment.event.q")
    public void ShipmentEvents(Message in){
        String message = new String(in.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        ShipmentEvent shipmentEvent = ShipmentEvent.fromJSON(message);
        Order update = new Order();
        if(shipmentEvent.getStatus().equals(ShipmentStatus.CANCELLED)){
            update.setStatus(OrderStatus.CANCELLED);
            if(shipmentEvent.getReason().equals(ShipmentCancelReason.CUSTOMER_REJECTED)){
                update.setStatus(OrderStatus.DELIVERED);
            }
        }
        if(shipmentEvent.getStatus().equals(ShipmentStatus.DELIVERED)){
            update.setStatus(OrderStatus.DELIVERED);
        }
        if(shipmentEvent.getStatus().equals(ShipmentStatus.IN_TRANSIT)){
            update.setStatus(OrderStatus.SHIPPED);
        }
        orderService.updateOrder(shipmentEvent.getOrderId(),update);
    }

}
