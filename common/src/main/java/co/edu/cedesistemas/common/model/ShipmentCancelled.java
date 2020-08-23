package co.edu.cedesistemas.common.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentCancelled {
    private String description;
    private CancelledReason reason;

    public enum CancelledReason {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED,
        NOT_APPLY
    }
}
