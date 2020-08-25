package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.event.ShipmentEvent;
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

    public void publishShippingEvent(Shipment shipment, ShipmentEvent.Status status) {
    	ShipmentEvent event = ShipmentEvent.builder()
                .orderId(shipment.getOrder().getId())
                .status(status)
                .build();

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = shipment.getId();
        String contentType = "application/json";

        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType(contentType);

        byte[] evtMsgBytes = json.getBytes();

        log.info("message to queue shipment");
        
        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "shipment.event." + msgId, message);
    }
}
