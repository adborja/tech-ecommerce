package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.common.event.OrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EventPublisherService {
    private final RabbitTemplate template;

    public void publishOrderEvent(Order order) {
        OrderEvent event = OrderEvent.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .storeId(order.getStoreId())
                 .build();
//.status(order.getStatus())
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
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "order.event." + msgId, message);
    }
}
