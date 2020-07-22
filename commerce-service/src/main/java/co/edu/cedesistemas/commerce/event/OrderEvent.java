package co.edu.cedesistemas.commerce.event;

import co.edu.cedesistemas.commerce.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderEvent {
    private String id; // order id
    private String storeId;
    private String userId;
    private LocalDateTime createdAt;
    private Order.Status status;

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