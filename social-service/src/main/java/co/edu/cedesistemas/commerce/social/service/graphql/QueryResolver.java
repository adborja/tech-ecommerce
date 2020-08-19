package co.edu.cedesistemas.commerce.social.service.graphql;

import java.util.List;

import org.springframework.stereotype.Service;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository.*;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.commerce.social.service.UserService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QueryResolver implements GraphQLQueryResolver{
	
	private UserService userService;
	private StoreService storeService;
	
	public User getUser(String id) {
		return userService.getById(id);
	}
	
	public List<StoreOccurrence> getRecommendStoreByProducts(String userId, String zone, String productType, int limit) {
		return storeService.recommendStoreByProducts(userId, zone, productType, limit);
	}
	
	public List<StoreOccurrence> getRecommendStoresByZoneAndProductType(String userId, String zone, String productType, int limit){
		return storeService.recommendStoresByZoneAndProductType(userId, zone, productType, limit);
	}
	
	public List<StoreOccurrence> getRecommendStoresByZone(String userId, String zone, int limit){
		return storeService.recommendStoresByZone(userId, zone, limit);
	}
}
