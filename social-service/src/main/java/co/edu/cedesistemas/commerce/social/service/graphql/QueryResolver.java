package co.edu.cedesistemas.commerce.social.service.graphql;

import co.edu.cedesistemas.commerce.social.model.User;

import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.commerce.social.service.PurchaseService;
import co.edu.cedesistemas.commerce.social.service.ProductService;
import co.edu.cedesistemas.commerce.social.service.EventPublisherService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service

//@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {
    private final UserService userService;
    private final StoreService storeService;
    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final EventPublisherService eventPublisherService;

    public QueryResolver(UserService userService, StoreService storeService, PurchaseService purchaseService, ProductService productService, EventPublisherService eventPublisherService) {
        this.userService = userService;
        this.storeService = storeService;
        this.purchaseService = purchaseService;
        this.productService = productService;
        this.eventPublisherService = eventPublisherService;
    }

    public User getuser(String id) {
        User user = userService.getById(id);
        if (user != null) {
            return user;
        }
        return null;
    }

    public  List<StoreRepository.StoreOccurrence> getrecommendStoreByProducts(String userId, String zone, String productType, Integer limit)
    {
        return storeService.recommendStoreByProducts(userId, zone, productType, limit).stream()
            .collect(Collectors.toList());
    }

    public  List<StoreRepository.StoreOccurrence> getrecommendStoresByZoneAndProductType(String userId, String zone, String productType, Integer limit)
    {
        return storeService.recommendStoresByZoneAndProductType(userId, zone, productType, limit).stream()
                .collect(Collectors.toList());
    }

    public  List<StoreRepository.StoreOccurrence> getrecommendStoresByZone(String userId, String zone, Integer limit)
    {
        return storeService.recommendStoresByZone(userId, zone, limit).stream()
                .collect(Collectors.toList());
    }

}
