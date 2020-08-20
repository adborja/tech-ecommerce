package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentCancelStatus;
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
    private ShipmentStatus status;
    private LocalDateTime createdAt;


    @Data
    public static class Cancel{
        private String id;
        private ShipmentCancelStatus cancelStatus;
        private String description;
    }
}

