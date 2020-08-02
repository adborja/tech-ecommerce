package co.edu.cedesistemas.commerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "commerce-service", path = "/api/v1")
public interface CommerceServiceClient {
    @PostMapping("/orders")
    ResponseEntity<String> createOrder (@RequestBody Order order);

    @Data
    @Builder
    class Order {
        private String userId;
        private String storeId;
    }
}
