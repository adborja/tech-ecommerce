package co.edu.cedesistemas.commerce.loyalty.event;

import co.edu.cedesistemas.commerce.loyalty.model.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class PaymentEvent {
    private String id;
    private String orderId;
    private String userId;
    private Float value;
    private PaymentStatus status;

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
