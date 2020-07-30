package co.edu.cedesistemas.commerce.inventory.controller;

import co.edu.cedesistemas.commerce.inventory.model.Product;
import co.edu.cedesistemas.commerce.inventory.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createProduct(@RequestBody final Product product) {
        Product created = service.createProduct(product);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }
}
