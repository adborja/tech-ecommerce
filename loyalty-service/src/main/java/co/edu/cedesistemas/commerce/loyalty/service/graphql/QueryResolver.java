package co.edu.cedesistemas.commerce.loyalty.service.graphql;

import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import co.edu.cedesistemas.commerce.loyalty.service.UserOrderService;
import co.edu.cedesistemas.commerce.loyalty.service.UserStoreService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserStoreService userStoreService;
    private final UserOrderService userOrderService;

    public Integer getLoyaltyPoints(final String userId, final String storeId) {
        UserStore userStore = getUserStore(userId, storeId);
        if (userStore != null) {
            return userStore.getPoints();
        }
        return 0;
    }

    public UserStore getUserStore(final String userId, final String storeId) {
        return userStoreService.getByStoreIdAndUserId(storeId, userId);
    }

    public List<UserStore> getTopNUsers(final String storeId, final int limit) {
        return userStoreService.getUsers(storeId).stream()
                .sorted((u1, u2) -> u2.getPoints() - u1.getPoints())
                .limit(limit)
                .collect(Collectors.toList());
    }
}
