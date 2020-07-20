package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.*;
import co.edu.cedesistemas.commerce.social.repository.LocationRepository;
import co.edu.cedesistemas.commerce.social.repository.ProductRepository;
import co.edu.cedesistemas.commerce.social.repository.ProductTypeRepository;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepository repository;
    private final LocationRepository locationRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;


    public Store createStore(Store store) {
        String country = store.getLocation().getCountry().toLowerCase().replace(" ", "_");
        String city = store.getLocation().getCity().toLowerCase().replace(" ", "_");
        String zone = store.getLocation().getZone().toLowerCase().replace(" ", "_");

        String locationName = country + ":" + city + ":" + zone;

        Location location = locationRepository.findByName(locationName).orElse(null);

        if (location == null) {
            location = new Location();
            location.setName(locationName);
            location.setCountry(country);
            location.setCity(city);
            location.setZone(zone);
        }
        store.setLocation(location);

        Set<String> productTypeNames = store.getProductTypeNames();
        productTypeNames.forEach(p -> {
            ProductType productType = productTypeRepository.findByName(p).orElse(null);
            if (productType == null) {
                productType = new ProductType();
                productType.setName(p.toLowerCase());
            }
            store.addProductType(productType);
        });
        return repository.save(store);
    }

    public Set<Store> getByUserLiked(final String userId) {
        return repository.findByUserLiked(userId);
    }

    public void addProduct(final String storeId, final String productId) throws Exception {
        // TODO: Implement method here
        Store store = repository.findById(storeId).orElse(null);
        Product prd = new Product();
        prd.setId(productId);
        if (store != null) {
            store.has(prd);
            repository.save(store);
        }
    }

    public void addProducts(final String storeId, final Set<String> productIds) throws Exception {
        Store store = getById(storeId);
        Set<Product> products = productRepository.findAllById(productIds);
        if (store != null) {
            store.has(products);
            repository.save(store);
        }
    }

    public List<StoreRepository.ProductOccurrence> getTopNProducts(final String storeId, final Integer limit) {
        return repository.findTopNProducts(storeId, limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZoneAndProductType(final String userId,
                                                                                     final String zone,
                                                                                     final String productType,
                                                                                     final Integer limit) {
        return repository.findRecommendationByProducts(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoreByProducts(final String userId, final String zone,
                                                                          final String productType, final Integer limit) {

        return repository.findRecommendationByProducts(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(final String userId, final String zone,
                                                                       final Integer limit) {

        return repository.findRecommendationByStores(userId, zone, limit);
    }

    public Store getById(String id) {
        return repository.findById(id).orElse(null);
    }

    private Store findStore(final String store) {
        return repository.findById(store).orElse(null);
    }


}
