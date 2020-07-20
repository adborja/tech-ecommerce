package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @PostMapping()
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
        try {
            Product created = service.createProduct(product);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status<?>> getProduct(@PathVariable String id) {
        try {
            Optional<Product> product = service.getProduct(id);
            if (product.isPresent())
                return DefaultResponseBuilder.defaultResponse(product, HttpStatus.OK);
            else
                return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(),ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<Status<?>> getProductByName(@RequestParam String name) {
        try {
            List<Product> products = service.getProductbyName(name);
            if (!products.isEmpty())
                return DefaultResponseBuilder.defaultResponse(products, HttpStatus.OK);
            else
                return DefaultResponseBuilder.errorResponse("products not found", null, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(),ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Status<?>> deleteProduct(@PathVariable String id) {
        try {
            Optional<Product> product = service.getProduct(id);
            if (product.isPresent()) {
                service.deleteProduct(product.get());
                return DefaultResponseBuilder.defaultResponse(product, HttpStatus.OK);
            }
            else
                return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(),ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product productUpdate) {

        try {
            if(id.equals(productUpdate.getId()) || productUpdate.getId() == null) {
                Optional<Product> product = service.getProduct(id);
                if (product.isPresent()) {
                    productUpdate.setId(id);
                    productUpdate = service.updateProduct(productUpdate);
                    return DefaultResponseBuilder.defaultResponse(productUpdate, HttpStatus.OK);
                } else
                    return DefaultResponseBuilder.errorResponse("Product not found ", null, HttpStatus.NOT_FOUND);
            }
            else
                return DefaultResponseBuilder.errorResponse("Bad Request ", null, HttpStatus.BAD_REQUEST);

        }
        catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
