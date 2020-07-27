package co.edu.cedesistemas.common.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationEvent {
    private String userId;
    private String storeId;
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

    public static RegistrationEvent fromJSON(final String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, RegistrationEvent.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public enum Status {
        USER_CREATED,
        USER_DELETED,
        FAILED
    }
}
