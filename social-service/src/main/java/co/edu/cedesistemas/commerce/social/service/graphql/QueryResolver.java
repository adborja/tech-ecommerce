package co.edu.cedesistemas.commerce.social.service.graphql;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.repository.UserRepository;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver{
    private final UserRepository repository;
    private final StoreService storeService;


    public User getUser(final String id){
        User user = repository.findById(id).orElse(null);
        if(user!=null){
            return user;
        }
        return null;
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts(final String userId, final String zone,
                                                                             final String productType, final Integer limit){
        return storeService.recommendStoreByProducts(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZoneAndProductType(final String userId, final String zone,
                                                                             final String productType, final Integer limit){
        return storeService.recommendStoresByZoneAndProductType(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(final String userId, final String zone,
                                                                                        final String productType, final Integer limit){
        return repository.recommendStoresByZone(userId, zone, productType, limit);
    }


}
