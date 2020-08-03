package co.edu.cedesistemas.commerce.loyalty.model;

import co.edu.cedesistemas.common.model.LoyaltyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("user_store")
public class UserStore {
    @Id
    private String id;
    private String userId;
    private String storeId;
    private LocalDateTime createdAt;
    private Integer points;
    private LoyaltyStatus status;
}
