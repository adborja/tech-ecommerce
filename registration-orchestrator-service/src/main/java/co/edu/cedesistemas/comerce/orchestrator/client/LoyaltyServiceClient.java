package co.edu.cedesistemas.comerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "loyalty-service", path = "/api/v1")
public interface LoyaltyServiceClient {

    @PostMapping("/user-stores")
    ResponseEntity<String> createUser(@RequestBody UserStore userStore);

    @DeleteMapping("/user-stores/{id}")
    ResponseEntity<String> deleteUserStore(@PathVariable String id);

    @Data
    @Builder
    class UserStore {
        private String id;
    }
}
