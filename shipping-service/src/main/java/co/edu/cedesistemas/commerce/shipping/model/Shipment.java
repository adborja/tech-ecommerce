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
    private Motivo motivo;
    private LocalDateTime createdAt;
    private String motivoDevolucion;

    public enum Status {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        CANCELLED
    }

    public enum Motivo {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED
    }
}
