package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Shipment {
    private String id;
    private Order order;
    private String trackNumber;
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    private String description;
    private CanceledDelivery canceledDelivery;

    public enum Status {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED
    }

}
