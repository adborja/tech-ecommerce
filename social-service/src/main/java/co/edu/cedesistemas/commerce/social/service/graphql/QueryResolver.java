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
        return userService.getById(userId);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts(final String userId, final String zone,
                                                                             final String productType, final Integer limit){
        return storeService.recommendStoreByProducts(userId,zone,productType,limit);
    }
}