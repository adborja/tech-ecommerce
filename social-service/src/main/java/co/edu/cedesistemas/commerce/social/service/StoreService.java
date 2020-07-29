package co.edu.cedesistemas.commerce.social.service;

import co.edu.cedesistemas.commerce.social.model.Location;
import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.ProductType;
import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.repository.LocationRepository;
import co.edu.cedesistemas.commerce.social.repository.ProductTypeRepository;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepository repository;
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
        locationRepository.save(location);
        store.setLocation(location);

        Set<String> productTypeNames = store.getProductTypeNames();
        productTypeNames.forEach(p -> {
            ProductType productType = productTypeRepository.findByName(p).orElse(null);
            if (productType == null) {
                productType = new ProductType();
                productType.setName(p.toLowerCase());
            }
            productTypeRepository.save(productType);
            store.addProductType(productType);

        });
        return repository.save(store);
    }

    public Set<Store> getByUserLiked(final String userId) {
        return repository.findByUserLiked(userId);
    }

    public void addProduct(final String storeId, final String productId) throws Exception {
        Store store = repository.findStore(storeId).orElse(null);
        if(store == null) {
            throw new Exception("No existe el almacen");
        }
        Product product = productService.getProduct(productId);
        store.has(product);
        repository.save(store);
    }

    public void addProducts(final String storeId, final Set<String> productIds) throws Exception {
        Store store = repository.findStore(storeId).orElse(null);
        if(store == null) {
            throw new Exception("No existe el almacen");
        }
        if(productIds.size() > 0) {
           Set<Product> products = productIds.stream().map(productService::getProduct).collect(Collectors.toSet());
            store.has(products);
        }
        else
        {
            throw new Exception("Lista de productos vacia");
        }
        repository.save(store);
    }


    @Cacheable(value = "social-topNStores",key = "#storid")
    public List<StoreRepository.ProductOccurrence> getTopNProducts(final String storeId, final Integer limit) {
        List<StoreRepository.ProductOccurrence> productOccurrences = repository.findTopNProducts(storeId,limit);
        return productOccurrences;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZoneAndProductType(final String userId,
                                                                                     final String zone,
                                                                                     final String productType,
                                                                                     final Integer limit) {
        List<StoreRepository.StoreOccurrence> storeOccurrences = repository.findRecommendationByStores(userId,zone,productType,limit);
        return storeOccurrences;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoreByProducts(final String userId, final String zone,
                                                                          final String productType, final Integer limit) {
        List<StoreRepository.StoreOccurrence> storeOccurrences = repository.findRecommendationByProducts(userId,zone,productType,limit);
        return storeOccurrences;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(final String userId, final String zone,
                                                                       final Integer limit) {
        List<StoreRepository.StoreOccurrence> storeOccurrences = repository.findRecommendationByStores(userId,zone,limit);
        return storeOccurrences;
    }

    public Store getById(String id) {
        return repository.findStore(id).orElse(null);
    }
}
