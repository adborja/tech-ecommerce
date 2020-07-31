package co.edu.cedesistemas.commerce.payment.controller;

import co.edu.cedesistemas.commerce.payment.client.TransactionRes;
import co.edu.cedesistemas.commerce.payment.model.Payment;
import co.edu.cedesistemas.commerce.payment.service.PaymentService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @PostMapping("/payments")
    public ResponseEntity<Status<?>> createPayment(@RequestBody Payment payment) {
        Payment created = service.createPayment(payment);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Status<?>> getById(@PathVariable String id) {
        Payment found = service.getById(id);
        if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        return DefaultResponseBuilder.errorResponse("payment not found", null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/payments/{id}/confirmation")
    public ResponseEntity<Status<?>> confirm(@PathVariable String id, @RequestBody TransactionRes resp) {
        Payment confirmed = service.confirm(id, resp);
        if (confirmed != null) {
            return DefaultResponseBuilder.defaultResponse(confirmed, HttpStatus.OK);
        }
        return DefaultResponseBuilder.errorResponse("payment not found", null, HttpStatus.NOT_FOUND);
    }
}
