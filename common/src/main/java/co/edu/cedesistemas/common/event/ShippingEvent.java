package co.edu.cedesistemas.common.event;

import co.edu.cedesistemas.common.model.OrderStatus;
import co.edu.cedesistemas.common.model.ShipmentStatus;
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
public class ShippingEvent {
    private String id;
    private String orderId;
    private String trackNumber;
    private String createdAt;
    private String status;
    private String reason;
    private String description;

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

    public static ShippingEvent fromJSON(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, ShippingEvent.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public enum ReasonStatus {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED
    }
}
