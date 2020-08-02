package co.edu.cedesistemas.commerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "loyalty-service", path = "/api/v1")
public interface LoyaltyServiceClient {
    @PostMapping("/user-stores")
    ResponseEntity<String> createUser(@RequestBody UserStore user);

    @Data
    @Builder
    class UserStore {
        private String id;
    }
}
