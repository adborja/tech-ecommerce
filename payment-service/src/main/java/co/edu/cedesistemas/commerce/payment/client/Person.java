package co.edu.cedesistemas.commerce.payment.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    private String merchantId;
    private String fullName;
    private String email;
    private String phone;
    private String dni;
}
