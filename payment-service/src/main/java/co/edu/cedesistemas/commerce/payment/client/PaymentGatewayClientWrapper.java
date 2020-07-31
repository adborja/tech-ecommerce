package co.edu.cedesistemas.commerce.payment.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentGatewayClientWrapper {
    private final PaymentGatewayClient client;

    public TransactionRes pay(TransactionReq req) {
        ResponseEntity<String> response = client.pay(req);
        JsonObject respJson = new JsonParser().parse(response.getBody()).getAsJsonObject();
        String transactionId = respJson.get("id").getAsString();
        String state = respJson.get("state").getAsString();
        String traceabilityCode = respJson.get("traceabilityCode").getAsString();
        String responseCode = respJson.get("responseCode").getAsString();
        String responseMessage = respJson.get("responseMessage").getAsString();

        return TransactionRes.builder()
                .id(transactionId)
                .state(state)
                .traceabilityCode(traceabilityCode)
                .responseCode(responseCode)
                .responseMessage(responseMessage)
                .build();
    }

}
