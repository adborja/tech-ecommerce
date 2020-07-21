package co.edu.cedesistemas.commerce.payment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("payment")
public class Payment {
    @Id
    private String id;
    private String orderId;
    private String userId;
    private Float value;
    private LocalDateTime createdAt;
    private PaymentStatus status;
}