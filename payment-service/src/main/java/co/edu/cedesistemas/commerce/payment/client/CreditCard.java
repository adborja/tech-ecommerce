package co.edu.cedesistemas.commerce.payment.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCard {
    private String number;
    private Integer securityCode;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String name;
}
