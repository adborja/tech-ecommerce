package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import co.edu.cedesistemas.common.event.PaymentEvent;
import co.edu.cedesistemas.common.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentEventConsumerService {
    private final UserOrderService userOrderService;

    @RabbitListener(queues = "payment.event.q")
    public void listenPaymentEvent(Message in) {
        String message = new String(in.getBody(), StandardCharsets.UTF_8);
        log.debug("received message: {}", message);
        PaymentEvent payment = PaymentEvent.fromJSON(message);

        if (payment == null) {
            log.error("could not read payment event");
            return;
        }

        if (payment.getStatus().equals(PaymentStatus.APPROVED)) {
            UserOrder userOrder = userOrderService
                    .registerOrder(payment.getOrderId(), payment.getUserId(), payment.getValue());
            log.info("loyalty points registered - user: {}, points: {}", userOrder.getId(), userOrder.getPoints());
        }
    }
}
