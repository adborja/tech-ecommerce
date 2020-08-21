package co.edu.cedesistemas.commerce.shipping.service;


import co.edu.cedesistemas.commerce.shipping.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.common.event.ShippingEvent;
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

    public void publishShippingEvent(Shipment shipment) {


        ShippingEvent event = ShippingEvent.builder()
                .id(shipment.getId())
                .orderId(shipment.getOrder().getId())
                .status(shipment.getStatus())
                .cancelledReason(shipment.getShipmentCancelledReason())
                .build();

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = shipment.getId();

        log.debug("sending event shipment with correlationId : {}", correlationId);

        publish(msgId, correlationId, json);


    }

    private void publish(final String msgId, final String correlationId, final String json) {

        log.info("sending event shipment with message {}", json);
        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType("application/json");

        byte[] evtMsgBytes = json.getBytes();

        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "shipment.event." + msgId, message);
    }
}
