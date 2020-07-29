package co.edu.cedesistemas.comerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "social-service", path = "/api/v1")
public interface SocialServiceClient {
    @PostMapping("/users")
    ResponseEntity<String> createUser(@RequestBody User user);

    @Data
    @Builder
    class User {
        private String id;
    }
}
