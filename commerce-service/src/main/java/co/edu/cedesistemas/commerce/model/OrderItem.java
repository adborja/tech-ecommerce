package co.edu.cedesistemas.commerce.model;

import lombok.Data;

@Data
public class OrderItem {
    private Product product;
    private Float finalPrice;
    private Integer quantity;
}
