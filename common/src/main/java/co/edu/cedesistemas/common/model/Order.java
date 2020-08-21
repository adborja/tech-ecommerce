package co.edu.cedesistemas.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String userId;
    private String storeId;
    private Address shippingAddress;
    private List<OrderItem> items;

    @Data
    public static class OrderItem {
        private String id;
        private Integer quantity;
    }
}