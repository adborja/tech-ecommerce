package co.edu.cedesistemas.common.event;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

    public static LoyaltyEvent fromJSON(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, LoyaltyEvent.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public enum Status {
        CREATED,
        DELETED,
        FAILED,
        PENDING
    }
}
