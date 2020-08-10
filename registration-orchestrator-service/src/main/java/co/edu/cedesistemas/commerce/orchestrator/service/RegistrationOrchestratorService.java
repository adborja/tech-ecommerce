package co.edu.cedesistemas.commerce.orchestrator.service;

import co.edu.cedesistemas.commerce.orchestrator.client.SocialServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.CommerceServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.LoyaltyServiceClient;
import co.edu.cedesistemas.commerce.orchestrator.client.RegistrationServiceClient;
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
        	log.info("Invoking feign client of social-service");
        	socialServiceClient.createUser(SocialServiceClient.User.builder()
                    .id(event.getUserId())
                    .build());
        } else if (event.getStatus().equals(RegistrationEvent.Status.FAILED)) {
        	log.info("Invoking compensate feign client of registration-service");
        	registrationServiceClient.deleteUser(event.getUserId());
        }
    }

    @RabbitListener(queues = "social.event.q")
    public void registerLoyaltyUser(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        SocialEvent event = SocialEvent.fromJSON(msg);
        if (event.getStatus().equals(SocialEvent.Status.CREATED)) {
            // Invocar cliente feign de loyalty para crear el usuario
        	log.info("Invoking feign client of loyalty-service");
        	loyaltyServiceClient.createUserStore(LoyaltyServiceClient.UserStore.builder()
        			.userId(event.getUserId())
        			.points(5)
        			.storeId("999") 
        			.build());
        } else if (event.getStatus().equals(SocialEvent.Status.FAILED)) {
        	log.info("Invoking compensate feign client of registration-service");
        	registrationServiceClient.deleteUser(event.getUserId());
        }
    }
    
    @RabbitListener(queues = "loyalty.event.q")
    public void registerOrder(Message in) {
        String msg = new String(in.getBody(), StandardCharsets.UTF_8);
        LoyaltyEvent event = LoyaltyEvent.fromJSON(msg);
        if (event.getStatus().equals(LoyaltyStatus.USER_CREATED)) {
            // Invocar cliente feign de commerce para crear la order
        	log.info("Invoking feign client of commerce-service");
        	commerceServiceClient.createOrder(CommerceServiceClient.Order.builder()
        			.userId(event.getUserId())
        			.storeId(event.getStoreId())
        			.build());
        } else if (event.getStatus().equals(LoyaltyStatus.FAILED)) {
        	log.info("Invoking compensate feign client of loyalty, social, registration service");
        	loyaltyServiceClient.deleteUserStore(event.getUserStoreId());
        	socialServiceClient.deleteUser(event.getUserId());
        	registrationServiceClient.deleteUser(event.getUserId());
        }
    }
    
	@RabbitListener(queues = "order.event.q")
	public void validateOrder(Message in) {
		String msg = new String(in.getBody(), StandardCharsets.UTF_8);
		OrderEvent event = OrderEvent.fromJSON(msg);

		if (event.getStatus().equals(OrderStatus.CREATED)) {
			log.info("order created");

		} else if (event.getStatus().equals(OrderStatus.FAILED)) {
			log.info("Invoking compensate feign client of commerce, loyalty, social, registration service");
			commerceServiceClient.deleteOrder(event.getId());
//			loyaltyServiceClient.deleteUserStore(event.getStoreId()); missing UserStoreId value
			socialServiceClient.deleteUser(event.getUserId());
			registrationServiceClient.deleteUser(event.getUserId());
		} else if (event.getStatus().equals(OrderStatus.CANCELLED)) {
			log.info("order cancelled");
		}

	}
}
