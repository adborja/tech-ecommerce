package co.edu.cedesistemas.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ShipmentCancellationReason {

    private String description;
    private String reason;

    public enum CancelledReason {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED
    }

}
