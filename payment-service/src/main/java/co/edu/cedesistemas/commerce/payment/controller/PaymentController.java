package co.edu.cedesistemas.commerce.payment.controller;

import co.edu.cedesistemas.commerce.payment.model.Payment;
import co.edu.cedesistemas.commerce.payment.service.PaymentService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("/payments/{id}/approve")
    public ResponseEntity<Status<?>> approve(@PathVariable String id) {
        Payment updated = service.approve(id);
        if (updated != null) {
            return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
        }
        return DefaultResponseBuilder.errorResponse("payment not found", null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/payments/{id}/reject")
    public ResponseEntity<Status<?>> reject(@PathVariable String id) {
        Payment updated = service.reject(id);
        if (updated != null) {
            return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
        }
        return DefaultResponseBuilder.errorResponse("payment not found", null, HttpStatus.NOT_FOUND);
    }
}
