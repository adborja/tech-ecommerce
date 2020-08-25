package co.edu.cedesistemas.common.event;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import co.edu.cedesistemas.common.model.PaymentStatus;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import co.edu.cedesistemas.common.model.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEvent {
    private String id;
    private String orderId;
    private String trackNumber;
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    private String reasonCancel;
    private String reasonDescription;

    @Override
    public String toString() {
        return toJSON();
    }

    public String toJSON() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            return "{}";
        }
    }

    public static ShipmentEvent fromJSON(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ShipmentEvent.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
