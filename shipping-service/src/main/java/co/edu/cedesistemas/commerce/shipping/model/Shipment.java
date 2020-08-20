package co.edu.cedesistemas.commerce.shipping.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@Document
public class Shipment {

    @Id
    private String id;
    private Order order;
    private String trackNumber;
    private Status status;
    private LocalDateTime createdAt;

    public enum Status {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED
    }
}
