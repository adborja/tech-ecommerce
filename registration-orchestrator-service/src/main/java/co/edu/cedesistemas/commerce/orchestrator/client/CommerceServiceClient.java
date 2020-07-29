package co.edu.cedesistemas.commerce.orchestrator.client;

import co.edu.cedesistemas.common.model.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "commerce-service", path = "/api/v1")
public interface CommerceServiceClient {
    @PostMapping("/orders")
    ResponseEntity<String> createUser(@RequestBody Order order);

    @Data
    @Builder
    class Order {
        private String id;
        private String userId;
        private String storeId;
        private String shippingAddressId;
        private OrderStatus status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<OrderItem> items;
        private Float orderValue;
    }

    @Data
    @Builder
    class OrderItem {
        private String productId;
        private Float finalPrice;
        private Integer quantity;
    }
}
