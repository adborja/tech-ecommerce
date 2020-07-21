package co.edu.cedesistemas.commerce.payment.event;

import co.edu.cedesistemas.commerce.payment.model.PaymentStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class PaymentEvent {
    private String id;
    private String orderId;
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
}
