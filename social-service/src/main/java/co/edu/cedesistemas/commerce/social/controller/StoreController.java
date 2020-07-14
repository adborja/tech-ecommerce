package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class StoreController {
    private final StoreService service;

    @PostMapping("/stores")
    public ResponseEntity<Status<?>> createStore(@RequestBody Store store) {
        try {
            Store created = this.service.createStore(store);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/stores/{id}/products/{productId}")
    public ResponseEntity<Status<?>> addStoreProduct(@PathVariable String id, @PathVariable String productId) {
        try {
            this.service.addProduct(id, productId);
            return DefaultResponseBuilder.defaultResponse("Updated", HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/{storeId}/products/top")
    public ResponseEntity<Status<?>> getTopNProducts(@PathVariable String storeId,
                                                     @RequestParam(required = false, defaultValue = "5") Integer limit) {
        try {
            List<StoreRepository.ProductOccurrence> topNProducts = this.service.getTopNProducts(storeId, limit);

            if (topNProducts.size() > 0) {
                return DefaultResponseBuilder.defaultResponse(topNProducts, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Top products in the store were not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
