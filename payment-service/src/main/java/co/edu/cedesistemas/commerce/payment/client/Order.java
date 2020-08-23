package co.edu.cedesistemas.commerce.payment.client;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
//jaime...
@Data
@Builder
public class Order {
    private String id;
    private String accountId;
    private String referenceCode;
    private String description;
    private String signature;
    private String notifyUrl;
    private Float value;
    private Person buyer;
}
