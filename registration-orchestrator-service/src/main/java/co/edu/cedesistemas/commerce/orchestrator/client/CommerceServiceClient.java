package co.edu.cedesistemas.commerce.orchestrator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.Builder;
import lombok.Data;

@FeignClient(name = "commerce-service", path = "/api/v1")
public interface CommerceServiceClient {

	@PostMapping("/orders")
	ResponseEntity<String> createOrder(@RequestBody Order order);
	
	@DeleteMapping("/orders/{id}")
	ResponseEntity<String> deleteOrder(@PathVariable(name = "id") String id);
	
	@Data
	@Builder
	class Order{
		private String userId;
		private String storeId;
	}
	
}
