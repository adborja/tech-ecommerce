package co.edu.cedesistemas.commerce.orchestrator.service;

import co.edu.cedesistemas.commerce.orchestrator.client.CommerceServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.LoyaltyServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.RegistrationServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.SocialServiceClient;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.event.SocialEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationOrchestratorService {
    private final SocialServiceClient socialServiceClient;
    private final LoyaltyServiceClient loyaltyServiceClient;
    private final RegistrationServiceClient registrationServiceClient;
    private  CommerceServiceClient commerceServiceClient;



    @RabbitListener(queues = "registration.event.q")
    public void registerSocialUser(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        RegistrationEvent event = RegistrationEvent.fromJSON(msg);

        if (event.getStatus().equals(RegistrationEvent.Status.USER_CREATED)) {
            socialServiceClient.createUser(SocialServiceClient.User.builder()
                    .id(event.getUserId())
                    .build());
        }
    }

    @RabbitListener(queues = "social.event.q")
    public void registerLoyaltyUser(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        SocialEvent event = SocialEvent.fromJSON(msg);

        if (event.getStatus().equals(SocialEvent.Status.CREATED)) {
            // Invocar cliente feign de loyalty para crear el usuario
            loyaltyServiceClient.createUser(LoyaltyServiceClient.UserStore.
                    builder()
                    .userId(event.getUserId())
                    .build());
        } else if (event.getStatus().equals(SocialEvent.Status.FAILED)) {
            //Invocar cliente feign de registration-service para compensar (eliminar) el usuario
            registrationServiceClient.deleteUser(event.getUserId());
        }
    }

    @RabbitListener(queues = "loyalty.event.q")
    public void registerOrderService(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        SocialEvent event = SocialEvent.fromJSON(msg);

        if (event.getStatus().equals(SocialEvent.Status.CREATED)) {
            // Invocar cliente feign de loyalty para crear el usuario
        } else if (event.getStatus().equals(SocialEvent.Status.FAILED)) {
            //Invocar cliente feign de registration-service para compensar (eliminar) el usuario
        }
    }


}
