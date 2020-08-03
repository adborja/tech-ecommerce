package co.edu.cedesistemas.commerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "social-service", path = "/api/v1")
public interface SocialServiceClient {

    @PostMapping("/users")
    ResponseEntity<String> createUser(@RequestBody User user);

    @DeleteMapping("/users/{id}")
    ResponseEntity<String> deleteUser(@RequestParam String id);

    @Data
    @Builder
    class User {
        private String id;
    }
}
