package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import co.edu.cedesistemas.commerce.loyalty.repository.UserOrderRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@RefreshScope
@Service
@RequiredArgsConstructor
@Slf4j
public class UserOrderService {
    private final UserOrderRepository repository;
    private final EventPublisherService publisherService;
    
    @Value("${app.loyalty.points-conversion-rate}")
    private Integer pointsConversionRate;

    public UserOrder registerOrder(@NotNull final String orderId, @NotNull String userId, @NotNull Float orderValue) {
        UserOrder uo = new UserOrder();
        uo.setId(orderId);
        uo.setUserId(userId);
        uo.setStatus(LoyaltyStatus.REGISTERED);
        uo.setOrderValue(orderValue);
//        uo.calculatePoints(Integer.parseInt(pointsConversionRate));

        uo.calculatePoints(pointsConversionRate);
        log.info("conversion rate obtained from property {}",pointsConversionRate);
        
        UserOrder result = repository.save(uo);

        publisherService.publishLoyaltyEvent(result);

        return result;
    }
}
