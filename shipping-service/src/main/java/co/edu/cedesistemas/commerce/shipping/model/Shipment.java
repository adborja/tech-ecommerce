package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentCancelledReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("shipment")
public class Shipment {
    @Id
    private String id;
    private Order order;
    private String trackNumber;
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    private ShipmentCancelledReason cancelledReason;
    private String cancelledDescription;
}
