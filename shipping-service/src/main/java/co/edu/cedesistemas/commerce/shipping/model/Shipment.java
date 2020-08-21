package co.edu.cedesistemas.commerce.shipping.model;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
public class Shipment extends RepresentationModel<Shipment> {
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
