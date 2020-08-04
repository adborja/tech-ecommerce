package co.edu.cedesistemas.commerce.social.service.graphql;


import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.commerce.social.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserService userService;
    private final StoreService storeService;
    private final StoreRepository storeRepository;


    // private final UserOrderService userOrderService;

    public User getUser(final String userId) {
        User user = userService.getById(userId);
        if (user != null) {
            return user;
        }
        return null;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoreByProducts(
            final String userId, final String zone, final String productType,
                final int limit ) {
        return storeRepository.findRecommendationByProducts(userId,zone,productType,limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZoneAndProductType(final String userId,
                                                                                     final String zone,
                                                                                     final String productType,
                                                                                     final Integer limit) {
        return storeRepository.findRecommendationByProducts(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(final String userId, final String zone,
                                                                       final Integer limit) {
        return   storeRepository.findRecommendationByStores(userId,userId,zone,limit);

    }
}
