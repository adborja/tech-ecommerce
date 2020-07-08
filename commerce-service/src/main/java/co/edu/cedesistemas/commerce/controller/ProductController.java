package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService service;

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createUser(@RequestBody Product product) {
        try {
            Product created = service.createProduct(product);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            Product found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("product not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String name) {
        try {
            List<Product> found = service.getByName(name);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody Product product) {
        try {
            if ((product != null && (product.getId() == null))) {
                Product updated = service.updateProduct(id, product);
                if (updated != null) return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
                else return DefaultResponseBuilder.defaultResponse("product not found", HttpStatus.NOT_FOUND);

            }else{
                return DefaultResponseBuilder.defaultResponse("bad request", HttpStatus.BAD_REQUEST);
            }

            } catch(Exception ex){
                return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Status<?>> getStoresByType(@PathVariable String id) {
        try {
            service.deleteProduct(id);
            return DefaultResponseBuilder.defaultResponse("delete product id: " + id, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}