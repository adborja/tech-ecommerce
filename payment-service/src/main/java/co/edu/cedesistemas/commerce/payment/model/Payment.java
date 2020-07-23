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
    private String storeId;
    private String orderId;
    private String userId;
    private String userEmail;
    private String userDni;
    private String userFullName;
    private String userPhone;
    private Float value;
    private String authorizationCode;
    private String responseMessage;
    private LocalDateTime createdAt;
    private PaymentStatus status;
    private CreditCard creditCard;

    @Data
    public static class CreditCard {
        private String number;
        private Integer securityCode;
        private Integer expirationMonth;
        private Integer expirationYear;
        private String name;
        private String brand;
    }
}