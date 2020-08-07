package co.edu.cedesistemas.commerce.orchestrator.service;

import co.edu.cedesistemas.commerce.orchestrator.client.CommerceServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.LoyaltyServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.RegistrationServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.SocialServiceClient;
import co.edu.cedesistemas.common.event.LoyaltyEvent;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.event.SocialEvent;
import co.edu.cedesistemas.common.model.LoyaltyStatus;
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
    private final RegistrationServiceClient registrationServiceClient;
    private final SocialServiceClient socialServiceClient;
    private final LoyaltyServiceClient loyaltyServiceClient;
    private final CommerceServiceClient commerceServiceClient;

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
        SocialEvent eventSocial = SocialEvent.fromJSON(msg);

        if (eventSocial.getStatus().equals(SocialEvent.Status.CREATED)) {
            loyaltyServiceClient.createUserStore(LoyaltyServiceClient.UserStore.builder().id(eventSocial.getUserId()).build());
        } else if (eventSocial.getStatus().equals(SocialEvent.Status.FAILED)) {
            registrationServiceClient.deleteUser(eventSocial.getUserId());
        }
    }

    @RabbitListener(queues = "loyalty.event.q")
    public void registerOrderService(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        LoyaltyEvent eventLoyal = LoyaltyEvent.fromJSON(msg);

        if (eventLoyal.getStatus().equals(LoyaltyStatus.USER_CREATED)) {
            commerceServiceClient.createOrder(CommerceServiceClient.Order.builder().id(eventLoyal.getOrderId()).build());
        } else if (eventLoyal.getStatus().equals(LoyaltyStatus.FAILED)) {
            loyaltyServiceClient.deleteLoyaltyUser(eventLoyal.getOrderId());
        }
    }

}
