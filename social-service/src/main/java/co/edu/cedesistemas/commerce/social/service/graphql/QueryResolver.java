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
    private final UserService user;
    private final StoreService store;

    public User getUser(String id){return user.getById(id);}

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts(
            String userId, String zone, String productType, int limit){
        return store.recommendStoreByProducts(userId,zone,productType,limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZoneAndProductType(
            String userId, String zone, String productType, int limit){
        return store.recommendStoresByZoneAndProductType(userId,zone,productType,limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(
            String userId, String zone, int limit){
        return store.recommendStoresByZone(userId,zone,limit);
    }
}
