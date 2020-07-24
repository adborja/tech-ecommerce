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

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository repository;
    private final LocationRepository locationRepository;
    private final ProductTypeRepository productTypeRepository;

    public Store createStore(Store store) {
    	log.info("creating store {}", store);
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

    public Store getById(final String id) {
    	log.info("getting store by id {}", id);
        return repository.findById(id).orElse(null);
    }


    public void addProduct(final String storeId, final String productId) throws Exception {
    	//log.info("adding product to store {}", productId+storeId);
    	log.info("adding product to store {} {}", productId,storeId);
        Store store = repository.findById(storeId).orElse(null);
        Product product = new Product();
        product.setId(productId);
        if (store != null) {
            store.has(product);
            repository.save(store);
        }
    }

    public void addProducts(final String storeId, final Set<String> productIds) throws Exception {
    	log.info("adding set product to store {}", storeId);
        Store store = repository.findById(storeId).orElse(null);       
        if (store != null) {
        	Set<Product> setProducts = new HashSet<Product>();
        	
        	for (String id : productIds) {
        		Product product = new Product();
        		product.setId(id);
        		setProducts.add(product);
             }
        	 store.has(setProducts);        	
             repository.save(store);
        }
    }


    public List<StoreRepository.ProductOccurrence> getTopNProducts(final String storeId, final Integer limit) {
        List<StoreRepository.ProductOccurrence> productOccurrenceList = repository.findTopNProducts(storeId,limit);
        return productOccurrenceList;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZoneAndProductType(final String userId,
                                                                                     final String zone,
                                                                                     final String productType,
                                                                                     final Integer limit) {
    	
    	List<StoreRepository.StoreOccurrence> productOccurrenceList = repository.findRecommendationByStores(userId,
    																										zone,
    																										productType,
    																										limit);
        return productOccurrenceList;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoreByProducts(final String userId, final String zone,
                                                                          final String productType, final Integer limit) {
    	List<StoreRepository.StoreOccurrence> productOccurrenceList = repository.findRecommendationByStores(userId,
																											zone,
																											productType
,																											limit);
    	return productOccurrenceList;
    }

    public List<StoreRepository.StoreOccurrence> recommendStoresByZone(final String userId, final String zone,
                                                                       final Integer limit) {
    	List<StoreRepository.StoreOccurrence> productOccurrenceList = repository.findRecommendationByStores(userId,
																											zone,
																											limit);
    	return productOccurrenceList;
    }

}
