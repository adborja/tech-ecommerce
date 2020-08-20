package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentCancelReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("shipment")
@Data
public class Shipment {
    @Id
    private String id;
    private Order order;
    private String trackNumber;
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    private Cancellation cancellation;

}
