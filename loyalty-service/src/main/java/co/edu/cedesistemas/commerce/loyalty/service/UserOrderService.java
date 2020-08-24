package co.edu.cedesistemas.commerce.loyalty.service;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import co.edu.cedesistemas.commerce.loyalty.repository.UserOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserOrderService {
    private final UserOrderRepository repository;
    private final UserStoreService userStoreService;
    private final EventPublisherService publisherService;

    @Value("${app.loyalty.points-conversion-rate}")
    private final Integer pointsConvertionRate;

    public UserOrder registerOrder(@NotNull final String orderId, @NotNull final String storeId,
                                   @NotNull String userId, @NotNull Float orderValue) {
        UserOrder uo = new UserOrder();
        uo.setId(orderId);
        uo.setStoreId(storeId);
        uo.setUserId(userId);
        uo.setStatus(LoyaltyStatus.REGISTERED);
        uo.setOrderValue(orderValue);
        uo.calculatePoints(pointsConvertionRate);
        log.info("valor obtenido desde el archivo de configuración: ",pointsConvertionRate);
        userStoreService.updatePoints(storeId, userId, uo.getPoints());

        UserOrder result = repository.save(uo);

        publisherService.publishLoyaltyEvent(result);

        return result;
    }

    public List<UserOrder> getUserOrders(final String userId) {
        return repository.findByUserId(userId);
    }
}
