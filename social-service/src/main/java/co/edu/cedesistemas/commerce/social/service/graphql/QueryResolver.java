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


    public User getUser(final String userId) {
        User user = userService.getById(userId);
        if (user != null) {
            return user;
        }
        return null;
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts(String userId, String zone, String productType, Integer limit){
        List<StoreRepository.StoreOccurrence> storeOccurrence = storeService.recommendStoreByProducts(userId,zone,productType,limit);

        return storeOccurrence;
    }

    public List<StoreRepository.StoreOccurrence>
        getRecommendStoresByZoneAndProductType(String userId, String zone,
                                               String productType, Integer limit) {
        List<StoreRepository.StoreOccurrence> storeOccurrences =
                storeService.recommendStoresByZoneAndProductType(userId,zone,productType,limit);
        return storeOccurrences;
    }

    public List<StoreRepository.StoreOccurrence>
    getRecommendStoresByZone(String userId, String zone, Integer limit) {
        List<StoreRepository.StoreOccurrence> storeOccurrences =
                storeService.recommendStoresByZone(userId, zone, limit);
        return storeOccurrences;
    }

}
