package co.edu.cedesistemas.commerce.shipping.model;

import lombok.Data;

import java.util.List;

@Data
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