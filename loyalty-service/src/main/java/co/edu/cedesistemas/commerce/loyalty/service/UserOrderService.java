package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import co.edu.cedesistemas.commerce.loyalty.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
@RefreshScope
@Slf4j
public class UserOrderService {
    private final UserOrderRepository repository;
    private final UserStoreService userStoreService;
    private final EventPublisherService publisherService;

    @Value("${app.loyalty.points-conversion-rate:50}")
    private Integer pointsConversionRate;


    public UserOrder registerOrder(@NotNull final String orderId, @NotNull final String storeId,
                                   @NotNull String userId, @NotNull Float orderValue) {
        UserOrder uo = new UserOrder();
        uo.setId(orderId);
        uo.setStoreId(storeId);
        uo.setUserId(userId);
        uo.setStatus(LoyaltyStatus.REGISTERED);
        uo.setOrderValue(orderValue);
        uo.calculatePoints(pointsConversionRate);

        log.info("pointsConversionRate..{}",pointsConversionRate);

        userStoreService.updatePoints(storeId, userId, uo.getPoints());

        UserOrder result = repository.save(uo);

        publisherService.publishLoyaltyEvent(result);

        return result;
    }

    public List<UserOrder> getUserOrders(final String userId) {
        return repository.findByUserId(userId);
    }
}
