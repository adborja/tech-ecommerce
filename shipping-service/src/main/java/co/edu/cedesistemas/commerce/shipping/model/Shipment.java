package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentCancelledReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@Document("shipment")
public class Shipment {

    @Id
    private String id;
    private Order order;
    private String trackNumber;
    private ShipmentStatus status;
    private ShipmentCancelledReason shipmentCancelledReason;
    private LocalDateTime createdAt;

}
