package co.edu.cedesistemas.common.event;

import co.edu.cedesistemas.common.model.ShippingStatus;
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
    private String trackNumber;
    private LocalDateTime createdAt;
    private String storeId;

    private String orderId;
    private String userId;
    private String userStoreId;
    private Float orderValue;
    private ShippingStatus status;


    private String city;
    private String country;
    private String state;
    private String street1;
    private String street2;
    private String street3;
    private String zip;

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

}
