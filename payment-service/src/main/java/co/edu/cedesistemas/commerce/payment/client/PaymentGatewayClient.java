package co.edu.cedesistemas.commerce.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-gateway", url = "${app.payment-gateway.api-base-uri}")
public interface PaymentGatewayClient {
    @PostMapping("/payments")
    ResponseEntity<String> pay(@RequestBody TransactionReq req);
}
