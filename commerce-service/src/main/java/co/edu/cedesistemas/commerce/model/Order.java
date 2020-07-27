package co.edu.cedesistemas.commerce.model;

import co.edu.cedesistemas.common.model.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Document("order")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private String id;
    private String userId;
    private String storeId;
    private String shippingAddressId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderItem> items;
    private Float orderValue;

    public void calculateValue() {
        if (items != null) {
            double sum = items.stream().mapToDouble(i -> i.getFinalPrice() * i.getQuantity()).sum();
            orderValue = (float) sum;
        }
    }
}