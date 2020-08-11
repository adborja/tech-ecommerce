package co.edu.cedesistemas.commerce.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
//@Builder
@Data
public class OrderItem {
    private String productId;
    private Float finalPrice;
    private Integer quantity;
}
