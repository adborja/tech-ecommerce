package co.edu.cedesistemas.commerce.orchestrator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Builder;
import lombok.Data;

@FeignClient(name = "loyalty-service", path = "/api/v1")
public interface LoyaltyServiceClient {
	
	@PostMapping("/user-stores")
	ResponseEntity<String> createUserStore(@RequestBody UserStore userStore);

	@DeleteMapping("/user-stores/{id}")
	ResponseEntity<String> deleteUserStore(@PathVariable(name = "id") String id);
	
	@Data
	@Builder
	class UserStore{
		private String userId;
		private String storeId;
		private Integer points;
	}
	
}
