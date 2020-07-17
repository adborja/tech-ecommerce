package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.AddressService;
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
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
        try {
            Product created = service.createProduct(product);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Status<?>> getProductById(@PathVariable String id) {
        try {
            Product found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getStoresByName(@RequestParam String name) {
        try {
            List<Product> found = service.getByName(name);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Status<?>> deleteProductById(@PathVariable String id) {
        try {
            service.deleteProductById(id);
            return DefaultResponseBuilder.defaultResponse("Product Deleted",HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            if (!id.equalsIgnoreCase(product.getId()) && product.getId() != null){
                return DefaultResponseBuilder.errorResponse("Bad Request",null,HttpStatus.BAD_REQUEST);
            }else {
                Product updatedProduct = service.updateProduct(id, product);
                if (updatedProduct != null)
                    return DefaultResponseBuilder.defaultResponse(updatedProduct, HttpStatus.OK);
                else
                    return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        }
    }


}