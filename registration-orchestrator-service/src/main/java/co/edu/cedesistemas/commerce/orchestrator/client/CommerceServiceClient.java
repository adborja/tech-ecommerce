package co.edu.cedesistemas.commerce.orchestrator.client;

import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.model.Status;
import lombok.Builder;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "commerce-service", path = "/api/v1")
public interface CommerceServiceClient {

    @PostMapping("/orders")
    public ResponseEntity<Status<?>> createOrder(@RequestBody Order order);

    @Data
    @Builder
    class Order{
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
    @Builder
    @Data
    class OrderItem{
        private String productId;
        private Float finalPrice;
        private Integer quantity;
    }
}
