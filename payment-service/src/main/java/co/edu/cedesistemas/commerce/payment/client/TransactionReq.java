package co.edu.cedesistemas.commerce.payment.client;

import lombok.Data;

import java.util.Map;

@Data
public class TransactionReq {
    private String id;
    private Order order;
    private Person payer;
    private CreditCard creditCard;
    private Map<String, ?> extraParameters;
    private String type;
    private String paymentMethod;
    private String paymentCountry;
    private String deviceSessionId;
    private String ipAddress;
    private String cookie;
    private String userAgent;
}
