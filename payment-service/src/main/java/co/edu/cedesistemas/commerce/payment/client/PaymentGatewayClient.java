package co.edu.cedesistemas.commerce.payment.client;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RefreshScope
@RibbonClient(name="payment-gateway")
@FeignClient(name = "payment-gateway", path = "/api/v1")
public interface PaymentGatewayClient {
    @PostMapping("/payments")
    ResponseEntity<String> pay(@RequestBody TransactionReq req);
}
