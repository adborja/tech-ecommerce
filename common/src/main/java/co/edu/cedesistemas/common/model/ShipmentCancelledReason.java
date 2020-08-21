package co.edu.cedesistemas.common.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentCancelledReason {

  private String description;
  private CancelledReason reason;

  public enum CancelledReason {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED,
        NOT_APPLY
 }

}
