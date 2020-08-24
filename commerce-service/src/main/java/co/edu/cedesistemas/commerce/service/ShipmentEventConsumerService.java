package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.event.ShipmentEvent;
import co.edu.cedesistemas.common.model.OrderStatus;
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
public class ShipmentEventConsumerService {
    private final OrderService orderService;

    @RabbitListener(queues = "shipment.event.q")
    public void listenPaymentEvent(Message in) {
        String message = new String(in.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        ShipmentEvent shipment = ShipmentEvent.fromJSON(message);

        if (shipment == null) {
            log.error("could not read shipment event");
            return;
        }

        if (shipment.getStatus().equals(ShipmentStatus.CANCELLED)) {
            String orderId = shipment.getOrderId();
            Order _order = new Order();
            _order.setStatus(OrderStatus.CANCELLED);
            Order order = orderService.updateOrder(orderId, _order);
            log.info("order updated - id: {}, status: {}", order.getId(), order.getStatus());
        }else if(shipment.getStatus().equals(ShipmentStatus.DELIVERED)){
            String orderId = shipment.getOrderId();
            Order _order = new Order();
            _order.setStatus(OrderStatus.DELIVERED);
            Order order = orderService.updateOrder(orderId, _order);
            log.info("order updated - id: {}, status: {}", order.getId(), order.getStatus());
        }
    }
}
