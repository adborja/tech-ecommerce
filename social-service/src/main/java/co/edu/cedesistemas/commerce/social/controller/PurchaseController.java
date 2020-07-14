package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.relation.PurchaseRelation;
import co.edu.cedesistemas.commerce.social.service.PurchaseService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@AllArgsConstructor
public class PurchaseController {
    private final PurchaseService service;

    @PutMapping("/purchases/users/{id}/products/{productId}")
    public ResponseEntity<Status<?>> purchase(@PathVariable String id, @PathVariable String productId) {
        try {
            PurchaseRelation created = service.purchase(id, productId);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/purchases/users/{id}")
    public ResponseEntity<Status<?>> purchase(@PathVariable String id, @RequestParam Set<String> productIds) {
        try {
            Set<PurchaseRelation> list = service.purchase(id, productIds);
            return DefaultResponseBuilder.defaultResponse(list, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
