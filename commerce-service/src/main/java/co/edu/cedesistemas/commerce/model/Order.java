package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Document("order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    private String id;
    private User user;
    private Store store;
    private Address shippingAddress;
    private Status status;
    private LocalDateTime createdAt;
    private List<OrderItem> items;

    public enum Status {
        CREATED,
        ACCEPTED,
        CONFIRMED,
        CANCELLED,
        SHIPPED,
        DELIVERED
    }
}