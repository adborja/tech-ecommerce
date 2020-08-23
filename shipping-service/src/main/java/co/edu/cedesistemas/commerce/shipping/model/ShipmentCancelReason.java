package co.edu.cedesistemas.commerce.shipping.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipmentCancelReason {
        private String reason;
        private StatusReason statusReason;

        public enum StatusReason {
            ADDRESS_NOT_FOUND,
            CUSTOMER_NOT_FOUND,
            CUSTOMER_REJECTED
        }
}
