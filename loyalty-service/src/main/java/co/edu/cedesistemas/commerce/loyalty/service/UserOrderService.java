package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import co.edu.cedesistemas.commerce.loyalty.repository.UserOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor
@Slf4j
public class UserOrderService {
    private final UserOrderRepository repository;
    private final EventPublisherService publisherService;

    private final int pointConversionRate;

    public UserOrder registerOrder(@NotNull final String orderId, @NotNull String userId, @NotNull Float orderValue) {
        UserOrder uo = new UserOrder();
        uo.setId(orderId);
        uo.setUserId(userId);
        uo.setStatus(LoyaltyStatus.REGISTERED);
        uo.setOrderValue(orderValue);

        log.info("************ pointConversions from registerOrder method "+ pointConversionRate);
        uo.calculatePoints(pointConversionRate);

        UserOrder result = repository.save(uo);

        publisherService.publishLoyaltyEvent(result);

        return result;
    }
}
