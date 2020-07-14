package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StoreController {
    private final StoreService service;

    @PostMapping("/stores")
    public ResponseEntity<Status<?>> createStore(@RequestBody Store store) {
        Store created = service.createStore(store);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @PutMapping("/stores/{id}/products/{productId}")
    public ResponseEntity<Status<?>> addStoreProduct(@PathVariable String id, @PathVariable String productId) {
        // TODO: Implement method here
        return null;
    }

    @GetMapping("/stores/{storeId}/products/top")
    public ResponseEntity<Status<?>> getTopNProducts(@PathVariable String storeId,
                                          @RequestParam(required = false, defaultValue = "5") Integer limit) {
        // TODO: Implement method here
        return null;
    }
}
