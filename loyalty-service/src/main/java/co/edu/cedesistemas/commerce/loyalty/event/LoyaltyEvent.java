package co.edu.cedesistemas.commerce.loyalty.event;

import co.edu.cedesistemas.commerce.loyalty.model.LoyaltyStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class LoyaltyEvent {
    private String orderId;
    private String userId;
    private Float orderValue;
    private LoyaltyStatus status;

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