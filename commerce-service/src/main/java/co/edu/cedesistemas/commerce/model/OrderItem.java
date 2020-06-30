package co.edu.cedesistemas.commerce.model;

import co.edu.cedesistemas.common.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class OrderItem implements Entity<String> {
    private String id;
    private String orderId;
    private Product product;
    private Float finalPrice;
    private Integer quantity;

    public String getId() {
        return id;
    }
}
