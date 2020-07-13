package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepository repository;

    public Set<Store> getByUserLiked(final String userId) {
        return repository.findByUserLiked(userId);
    }

    public void addProduct(final String storeId, final String productId) throws Exception {
        // TODO: Implement method here
    }

    public void addProduct(final String storeId, final Set<String> productIds) throws Exception {
        // TODO: Implement method here
    }


    public List<StoreRepository.ProductOccurrence> getTopNProducts(final String storeId, final Integer limit) {
        // TODO: Implement method here
        return null;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZoneAndProductType(final String userId,
                                                                                     final String zone,
                                                                                     final String productType,
                                                                                     final Integer limit) {
        // TODO: Implement method here
        return null;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoreByProducts(final String userId, final String zone,
                                                                          final String productType, final Integer limit) {
        // TODO: Implement method here
        return null;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(final String userId, final String zone,
                                                                       final Integer limit) {
        // TODO: Implement method here
        return null;
    }
}
