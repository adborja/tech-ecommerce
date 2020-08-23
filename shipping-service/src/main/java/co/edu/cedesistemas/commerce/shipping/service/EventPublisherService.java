package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.event.ShippingEvent;
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

    public void publishUpdateStatusEvent(Shipment shipment, ShippingEvent.Status status){
        ShippingEvent shippingEvent = ShippingEvent.builder()
                .id(shipment.getId())
                .status(status)
                .build();

        String json = shippingEvent.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = shipment.getId();

        publish(msgId, correlationId, json);

    }

    private void publish(final String msgId, final String correlationId, final String json) {
        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType("application/json");

        byte[] evtMsgBytes = json.getBytes();

        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "shipment.event." + msgId, message);
    }
}
