package co.edu.cedesistemas.commerce.orchestrator.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "commerce-service", path = "/api/v1")
public interface CommerceServiceClient {

    @PostMapping("/orders")
    ResponseEntity<String> createOrder(@RequestBody User user);

    @DeleteMapping("/orders/{id}")
    ResponseEntity<String> deleteOrder(@RequestParam String id);

    @Data
    @Builder
    class User {
        private String id;
        private String name;
        private String lastName;
        private String email;
    }
}
