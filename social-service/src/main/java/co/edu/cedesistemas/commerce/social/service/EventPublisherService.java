package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.config.RabbitMQConfig;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.common.event.SocialEvent;
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

    public void publishSocialEvent(User user, SocialEvent.Status status) {
        SocialEvent event = SocialEvent.builder()
                .userId(user.getId())
                .status(status)
                .build();

        String json = event.toJSON();
        String msgId = UUID.randomUUID().toString();
        String correlationId = user.getId();
        String contentType = "application/json";

        MessageProperties msgProps = new MessageProperties();
        msgProps.setMessageId(msgId);
        msgProps.setCorrelationId(correlationId);
        msgProps.setContentType(contentType);

        byte[] evtMsgBytes = json.getBytes();

        Message message = new Message(evtMsgBytes, msgProps);
        template.send(RabbitMQConfig.TOPIC_EXCHANGE, "social.event." + msgId, message);
    }
}
