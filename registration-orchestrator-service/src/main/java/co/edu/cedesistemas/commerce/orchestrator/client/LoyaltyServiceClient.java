package co.edu.cedesistemas.commerce.orchestrator.client;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@FeignClient(name = "loyalty-service", path = "/api/v1")
public interface LoyaltyServiceClient {
    @PostMapping("/user-stores")
    ResponseEntity<String> createUser(@RequestBody UserStore userStore);

    @Data
    @Builder
    class UserStore {
        private String id;
        private String userId;
        private String storeId;
        private LocalDateTime createdAt;
        private Integer points;
        private LoyaltyStatus status;
    }
}