package co.edu.cedesistemas.common.event;

import co.edu.cedesistemas.common.model.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEvent {
    private String id;
    private String orderId;
    private String storeId;
    private String userId;
    private Float value;
    private PaymentStatus status;

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

    public static PaymentEvent fromJSON(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, PaymentEvent.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
