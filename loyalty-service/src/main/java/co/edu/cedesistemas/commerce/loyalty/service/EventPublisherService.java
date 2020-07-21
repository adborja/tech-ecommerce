package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.commerce.loyalty.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.loyalty.event.LoyaltyEvent;
import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
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

    public void publishLoyaltyEvent(UserOrder order) {
        LoyaltyEvent event = new LoyaltyEvent();
        event.setOrderId(order.getId());
        event.setUserId(order.getUserId());
        event.setOrderValue(order.getOrderValue());
        event.setStatus(order.getStatus());

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = order.getId();

        publish(msgId, correlationId, json);

    }

    private void publish(final String msgId, final String correlationId, final String json) {
        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType("application/json");

        byte[] evtMsgBytes = json.getBytes();

        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "loyalty.event." + msgId, message);
    }
}
