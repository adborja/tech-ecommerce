package co.edu.cedesistemas.common.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingEvent {
    private String id;
    private String trackNumber;
    private Status status;

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

    public enum Status {
        CREATED,
        IN_TRANSIT,
        DELIVERED,
        CANCELED
    }

}
