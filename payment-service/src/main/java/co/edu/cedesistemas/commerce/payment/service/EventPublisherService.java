package co.edu.cedesistemas.commerce.payment.service;

import co.edu.cedesistemas.commerce.payment.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.payment.event.PaymentEvent;
import co.edu.cedesistemas.commerce.payment.model.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class EventPublisherService {
    private final RabbitTemplate template;

    public void publishPaymentEvent(Payment payment) {
        PaymentEvent event = new PaymentEvent();
        event.setId(payment.getId());
        event.setOrderId(payment.getOrderId());
        event.setUserId(payment.getUserId());
        event.setStatus(payment.getStatus());
        event.setValue(payment.getValue());

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = payment.getId();
        String contentType = "application/json";

        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType(contentType);

        byte[] evtMsgBytes = json.getBytes();

        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "payment.event." + msgId, message);
    }
}
