package co.edu.cedesistemas.commerce.orchestrator.client;

import co.edu.cedesistemas.common.model.Status;
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
    ResponseEntity<Status<?>> createUserStore(@RequestBody UserStore userStore);

    @DeleteMapping("/user-stores/{id}")
    ResponseEntity<Status<?>> deleteLoyaltyUser(@PathVariable String id);

    @Data
    @Builder
    class UserStore{
        private String id;
    }
}
