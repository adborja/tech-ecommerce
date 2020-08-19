package co.edu.cedesistemas.commerce.orchestrator.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.Builder;
import lombok.Data;

@FeignClient(path = "/api/v1", name = "registration-service")
public interface RegistrationServiceClient {

	@DeleteMapping("/users/{id}")
	ResponseEntity<String> deleteUser(@PathVariable(name = "id") String id);

	@Data
	@Builder
	class User {
		private String id;
	}

}
