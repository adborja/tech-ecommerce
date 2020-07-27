package co.edu.cedesistemas.commerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem extends RepresentationModel<OrderItem> {
    private String productId;
    private Float finalPrice;
    private Integer quantity;
}
