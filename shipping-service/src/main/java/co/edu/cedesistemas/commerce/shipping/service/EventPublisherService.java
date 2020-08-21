package co.edu.cedesistemas.commerce.shipping.service;

import co.edu.cedesistemas.commerce.shipping.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.shipping.model.Shipment;
import co.edu.cedesistemas.commerce.shipping.model.Address;
import co.edu.cedesistemas.commerce.shipping.model.Order;
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

    public void publishShippingEvent(Order order) {
        ShippingEvent event = ShippingEvent.builder()
                .orderId(order.getId())
                .storeId(order.getStoreId())
                .userId(order.getUserId())
                .build();

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = order.getId();

        publish(msgId, correlationId, json);

    }

    public void publishShippingEvent(Address address) {
        ShippingEvent event = ShippingEvent.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .state(address.getState())
                .street1(address.getStreet1())
                .zip(address.getZip())
                .build();

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = address.getId();

        publish(msgId, correlationId, json);

    }

    public void publishShippingEvent(Shipment shipment) {
        ShippingEvent event = ShippingEvent.builder()
                .id(shipment.getId())
                .trackNumber(shipment.getTrackNumber())
                .createdAt(shipment.getCreatedAt())
                .storeId(shipment.getOrder().getStoreId())
                .userId(shipment.getOrder().getUserId())
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
