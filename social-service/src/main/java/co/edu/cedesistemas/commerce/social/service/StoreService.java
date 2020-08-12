package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Location;
import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.ProductType;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.repository.LocationRepository;
import co.edu.cedesistemas.commerce.social.repository.ProductTypeRepository;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductService productService;

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

        return storeRepository.save(store);
    }

    public Store getStoreById(String storeId){
        return storeRepository.findById(storeId).orElse(null);
    }

    public Set<Store> getByUserLiked(final String userId) {
        return storeRepository.findByUserLiked(userId);
    }

    public void addProduct(final String storeId, final String productId) throws Exception {
        storeRepository.findById(storeId)
                .map(storeFound -> {
                    storeFound.has(productService.getProduct(productId));
                    return storeFound;
                }).ifPresent(storeRepository::save);
    }

    public void addProducts(final String storeId, final Set<String> productIds) throws Exception {
        storeRepository.findById(storeId)
                .map(storeFound -> {
                    storeFound.has(setProductsFound(productIds));
                    return storeFound;
                }).ifPresent(storeRepository::save);

    }

    private Set<Product> setProductsFound(Set<String> productsIds){
        Set<Product> products = new HashSet<>();
        productsIds.stream().forEach(productId -> products.add(productService.getProduct(productId)));

        return products;
    }

    @Cacheable(value = "social_get_top_n_products", key = "#storeId" + "#limit")
    public List<StoreRepository.ProductOccurrence> getTopNProducts(final String storeId, final Integer limit) {
        return storeRepository.findTopNProducts(storeId, limit);
    }

    @Cacheable(value = "social_recommend_stores_by_zone_and_product_type", key = "#userId" + "#zone" + "#productType" + "#limit")
    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZoneAndProductType(final String userId,
                                                                                     final String zone,
                                                                                     final String productType,
                                                                                     final Integer limit) {
        return storeRepository.findRecommendationByProducts(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoreByProducts(final String userId, final String zone,
                                                                          final String productType, final Integer limit) {
        return storeRepository.findRecommendationByStores(userId, zone, productType, limit);
    }

    public List<StoreRepository.StoreOccurrence> getRecommendStoresByZone(final String userId, final String zone,
                                                                       final Integer limit) {
        return storeRepository.findRecommendationByStores(userId, zone, limit);
    }

    public Store getById(String id) {
        return storeRepository.findById(id).orElse(null);
    }
}
