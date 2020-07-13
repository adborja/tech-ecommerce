package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "id")
@Document("order")
public class Order extends RepresentationModel<Order> {
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