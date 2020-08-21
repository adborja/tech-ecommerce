package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.event.PaymentEvent;
import co.edu.cedesistemas.common.event.ShippingEvent;
import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.model.PaymentStatus;
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
    public void listenShipmentEvent(Message in) {
        String message = new String(in.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        PaymentEvent payment = PaymentEvent.fromJSON(message);
        ShippingEvent shippingEvent = ShippingEvent.fromJSON(message);

        if (shippingEvent == null) {
            log.error("could not read shipment event");
            return;
        }

        OrderStatus orderStatus = null;
        switch (shippingEvent.getStatus()){
            case CREATED:
            case IN_TRANSIT:
                orderStatus = OrderStatus.SHIPPED;
                break;
            case DELIVERED:
                orderStatus = OrderStatus.DELIVERED;
                break;
            case CANCELLED:
                orderStatus = OrderStatus.CANCELLED;
                break;
        }

        if (orderStatus != null) {
            String orderId = shippingEvent.getOrderId();
            Order _order = new Order();
            _order.setStatus(orderStatus);
            Order order = orderService.updateOrder(orderId, _order);
            log.info("order updated - id: {}, status: {}", order.getId(), order.getStatus());
        }
    }

}
