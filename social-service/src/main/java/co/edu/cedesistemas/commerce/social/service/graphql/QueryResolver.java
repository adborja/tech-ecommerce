package co.edu.cedesistemas.commerce.social.service.graphql;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.commerce.social.service.UserService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final UserService userService;
    private final StoreService storeService;

    public User getUser(final String id) {
        return this.userService.getById(id);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts(String userId, String zone, String productType,
                                                                             Integer limit) {
        return this.storeService.recommendStoreByProducts(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZoneAndProductType(String userId, String zone, String productType,
                                                                                        Integer limit) {
        return this.storeService.recommendStoresByZoneAndProductType(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZone(String userId, String zone, Integer limit) {
        return this.storeService.recommendStoresByZone(userId, zone, limit);
    }
}
