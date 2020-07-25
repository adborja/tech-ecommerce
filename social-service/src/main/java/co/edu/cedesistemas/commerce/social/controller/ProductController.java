package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.Product;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.service.ProductService;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createUser(@RequestBody Product product) {
        Product user1 = service.createProduct(product.getId());
        return DefaultResponseBuilder.defaultResponse(user1, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Status<?>> getProduct(@PathVariable String id) {
        try {
            Product product = service.getById(id);
            return DefaultResponseBuilder.defaultResponse(product,HttpStatus.OK);
        } catch (Exception e) {
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}