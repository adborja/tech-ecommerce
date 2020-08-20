package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("shipment")
public class Shipment {
    private String id;
    private Order order;
    private String trackNumber;
    private LocalDateTime createdAt;
    private ShipmentStatus status;
    private ReasonStatus reason;
    private String description;

    public enum ReasonStatus {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED
    }
}
