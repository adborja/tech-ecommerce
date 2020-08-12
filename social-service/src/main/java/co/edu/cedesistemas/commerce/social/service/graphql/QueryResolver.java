package co.edu.cedesistemas.commerce.social.service.graphql;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.ProductService;
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

    public User getUser(final String id){
        return userService.getById(id);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts
            (final String userId,final String zone,final String productType,final Integer limit) {
        return storeService.getRecommendStoreByProducts( userId,  zone,  productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZoneAndProductType(final String userId,final String zone,final String productType,final Integer limit) {
        return storeService.getRecommendStoresByZoneAndProductType(userId,zone,productType,limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZone(final String userId,final String zone,final Integer limit){
        return storeService.getRecommendStoresByZone(userId,zone,limit);
    }
}
