package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.event.ShippingEvent;
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
public class ShippingEventConsumerService {
    private final OrderService orderService;

    @RabbitListener(queues = "shipment.event.q")
    public void listenDeliverStatusEvent(Message in) {
        String message = new String(in.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        ShippingEvent shippingEvent = ShippingEvent.fromJSON(message);

        if (shippingEvent == null) {
            log.error("could not read shipment event");
            return;
        }

        if (shippingEvent.getStatus().equals(ShipmentStatus.CANCELLED)) {
            String orderId = shippingEvent.getOrderId();
            Order _order = new Order();
            _order.setStatus(OrderStatus.CANCELLED);
            log.debug("received commerce reason: {}", shippingEvent.getReason());
            log.debug("received commerce description: {}", shippingEvent.getDescription());
            Order order = orderService.updateOrder(orderId, _order);
            log.info("order updated - id: {}, status: {}", order.getId(), order.getStatus());
        }else if (shippingEvent.getStatus().equals(ShipmentStatus.DELIVERED)) {
            String orderId = shippingEvent.getOrderId();
            Order _order = new Order();
            _order.setStatus(OrderStatus.DELIVERED);
            Order order = orderService.updateOrder(orderId, _order);
            log.info("order updated - id: {}, status: {}", order.getId(), order.getStatus());
        }
    }
}
