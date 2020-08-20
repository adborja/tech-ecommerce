package co.edu.cedesistemas.commerce.shipping.model;

import co.edu.cedesistemas.common.model.ShipmentCancelReason;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cancellation {

    private ShipmentCancelReason reason;
    private String description;
}
