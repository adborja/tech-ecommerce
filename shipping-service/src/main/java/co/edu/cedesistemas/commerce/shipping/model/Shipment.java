package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentCancelled;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Shipment {
    private String id;
    private Order order;
    private String trackNumber;
    private ShipmentStatus status;
    private ShipmentCancelled shipmentCancelled;
    private LocalDateTime createdAt;


}
