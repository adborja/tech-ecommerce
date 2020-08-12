package co.edu.cedesistemas.commerce.orchestrator.service;

import co.edu.cedesistemas.commerce.orchestrator.client.CommerceServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.LoyaltyServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.RegistrationServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.SocialServiceClient;
import co.edu.cedesistemas.common.event.LoyaltyEvent;
import co.edu.cedesistemas.common.event.OrderEvent;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.event.SocialEvent;
import co.edu.cedesistemas.common.model.LoyaltyStatus;
import co.edu.cedesistemas.common.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationOrchestratorService {
    private final SocialServiceClient socialServiceClient;
    private final RegistrationServiceClient registrationServiceClient;
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
        SocialEvent event = SocialEvent.fromJSON(msg);

        if (event.getStatus().equals(SocialEvent.Status.CREATED)) {
            // Invocar cliente feign de loyalty para crear el usuario
            loyaltyServiceClient.createUserStore(LoyaltyServiceClient
                    .UserStore.builder()
                    .userId(event.getUserId())
                    .storeId(UUID.randomUUID().toString())
                    .build());
        } else if (event.getStatus().equals(SocialEvent.Status.FAILED)) {
            registrationServiceClient.deleteUser(event.getUserId());
            //Invocar cliente feign de registration-service para compensar (eliminar) el usuario
        }
    }

    @RabbitListener(queues = "loyalty.event.q")
    public void createOrderCommerceService(Message in){
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        LoyaltyEvent event = LoyaltyEvent.fromJSON(msg);
        if(event.getStatus().equals(LoyaltyStatus.USER_CREATED)){
            commerceServiceClient.createOrder(CommerceServiceClient.Order.builder()
                    .shippingAddressId("pending")
                    .storeId(event.getStoreId())
                    .userId(event.getUserId())
                    .items(Arrays.asList(CommerceServiceClient.OrderItem.builder()
                            .productId("gift")
                            .quantity(1)
                            .finalPrice(0f)
                            .build()))
                    .build());
        } else if(event.getStatus().equals(LoyaltyStatus.FAILED)){
            loyaltyServiceClient.deleteUserStore(event.getUserId());
        }
    }

    @RabbitListener(queues = "order.event.q")
    public void rollbackOrderCommerceService(Message in){
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        OrderEvent event = OrderEvent.fromJSON(msg);
        if(event.getStatus().equals(OrderStatus.CREATED)){

         } else if(event.getStatus().equals(LoyaltyStatus.FAILED)){
            loyaltyServiceClient.deleteUserStore(event.getUserId());
        }
    }
}
