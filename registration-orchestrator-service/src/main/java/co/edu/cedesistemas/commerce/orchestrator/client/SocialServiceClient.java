package co.edu.cedesistemas.commerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "social-service", path = "/api/v1")
public interface SocialServiceClient {
    @PostMapping("/users")
    ResponseEntity<String> createUser(@RequestBody User user);
    
    @DeleteMapping("/users/{id}")
    ResponseEntity<String> deleteUser(@PathVariable(name = "id") String id);

    @Data
    @Builder
    class User {
        private String id;
    }
}
