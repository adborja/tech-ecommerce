package co.edu.cedesistemas.commerce.payment.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRes {
    private String id;
    private String state;
    private String traceabilityCode;
    private String authorizationCode;
    private String responseCode;
    private String errorCode;
    private String responseMessage;
}
