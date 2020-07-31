package co.edu.cedesistemas.commerce.orchestrator.service;

import co.edu.cedesistemas.commerce.orchestrator.client.LoyaltyServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.OrderServiceClient;
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
    private final SocialServiceClient socialServiceClient;
    private final LoyaltyServiceClient loyaltyServiceClient;
    private final OrderServiceClient orderServiceClient;
    private final RegistrationServiceClient registrationServiceClient;


    /**
     * social lee de la cola de registration
     * si la creacion del usuario en registration es exitosa crea un usuario en social
     */
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

    /**
     * loyalty lee  de la cola de social
     * si la creacion del usuario en social es exitosa crea un User store en loyalty
     * si la creacion del usuario en social es fallida, elimina el usuario de registration
     *
     * @param in
     */
    @RabbitListener(queues = "social.event.q")
    public void registerLoyaltyUser(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        SocialEvent event = SocialEvent.fromJSON(msg);

        if (event.getStatus().equals(SocialEvent.Status.CREATED)) {
            this.loyaltyServiceClient.createUserStore(LoyaltyServiceClient.UserStore.builder()
                    .userId(event.getUserId())
                    .build());
        } else if (event.getStatus().equals(SocialEvent.Status.FAILED)) {
            this.registrationServiceClient.deleteUser(event.getUserId());
        }
    }

    /**
     * order lee de la cola de loyalty
     * si la creacion de user store es exitosa crea una order en commerce
     * si la creacion del user store es fallida, elimina el user store en loyalty
     *
     * @param in
     */
    @RabbitListener(queues = "loyalty.event.q")
    public void registerOrderUser(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        LoyaltyEvent event = LoyaltyEvent.fromJSON(msg);

        if (event.getStatus().equals(LoyaltyStatus.USER_CREATED)) {
            this.orderServiceClient.createOrder(OrderServiceClient.Order.builder()
                    .userId(event.getUserId()).storeId(event.getStoreId()).build());
        } else if (event.getStatus().equals(SocialEvent.Status.FAILED)) {
            this.loyaltyServiceClient.deleteUserStore(event.getUserId());
        }
    }
}
