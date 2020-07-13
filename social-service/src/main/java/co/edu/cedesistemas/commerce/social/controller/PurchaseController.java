package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.service.PurchaseService;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@AllArgsConstructor
public class PurchaseController {
    private final PurchaseService service;

    @PutMapping("/purchases/users/{id}/products/{productId}/purchase")
    public ResponseEntity<Status<?>> purchase(@PathVariable String id, @PathVariable String productId) {
        // TODO: Implement method here
        return null;
    }
}
