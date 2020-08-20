package co.edu.cedesistemas.common.event;

import co.edu.cedesistemas.common.model.ShipmentCancelReason;
import co.edu.cedesistemas.common.model.ShipmentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEvent {

    private String id; // shipment id
    private String orderId;
    private ShipmentStatus status;
    private ShipmentCancelReason reason;
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
