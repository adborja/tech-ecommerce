package co.edu.cedesistemas.commerce.loyalty.model;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("user_order")
public class UserOrder {
    @Id
    private String id; //order id
    private String storeId;
    private String userId;
    private Float orderValue;
    private LoyaltyStatus status;
    private Integer points;

    public void calculatePoints() {
        if (points == null) {
            points = 0;
        }
        points = points + (int) (orderValue / 1000);
    }
}
