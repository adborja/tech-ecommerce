package co.edu.cedesistemas.commerce.registration.services;

import co.edu.cedesistemas.commerce.registration.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.common.event.RegistrationEvent;
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

    public void publishRegistrationEvent(User user, RegistrationEvent.Status status){
        RegistrationEvent registrationEvent = RegistrationEvent.builder()
                .userId(user.getId())
                .status(status)
                .build();

        String json = registrationEvent.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = user.getId();

        publish(msgId, correlationId, json);

    }

    private void publish(final String msgId, final String correlationId, final String json) {
        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType("application/json");

        byte[] evtMsgBytes = json.getBytes();

        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "registration.event." + msgId, message);
    }
}
