package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.event.OrderEvent;
import co.edu.cedesistemas.common.event.ShipmentEvent;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EventPublisher {
    private final RabbitTemplate template;

    public void publishShipmentEvent(Shipment shipment) {
        ShipmentEvent event = ShipmentEvent.builder()
                .id(shipment.getId())
                .description(shipment.getCancellation().getDescription())
                .reason(shipment.getCancellation().getReason())
                .status(shipment.getStatus())
                .orderId(shipment.getOrder().getId())
                .build();

        String json = event.toJSON();
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
