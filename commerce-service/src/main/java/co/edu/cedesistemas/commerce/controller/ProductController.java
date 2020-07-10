package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
        try {
            Product created = this.productService.createProduct(product);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Status<?>> getProducts(@PathVariable String id) {
        try {
            Optional<Product> foundProduct = this.productService.findById(id);

            if (foundProduct.isPresent()) {
                List<Product> products = List.of(foundProduct.get());
                return DefaultResponseBuilder.defaultResponse(products, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getProductByName(@PathParam("name") String name) {
        try {
            List<Product> products = this.productService.getProductByNamePrefix(name);
            if (products.size() > 0) {
                return DefaultResponseBuilder.defaultResponse(products, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product) {

        if (!StringUtils.isEmpty(product.getId())) {
            return DefaultResponseBuilder.defaultResponse("Bad request", HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<Product> updatedProduct = this.productService.updateProduct(id, product);

            if (updatedProduct.isPresent()) {
                return DefaultResponseBuilder.defaultResponse(updatedProduct.get(), HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Couldn't update product", null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Status<?>> deleteProduct(@PathVariable String id) {
        try {
            boolean deleted = this.productService.deleteProduct(id);
            if (deleted) {
                return DefaultResponseBuilder.defaultResponse(true, HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse(false, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
