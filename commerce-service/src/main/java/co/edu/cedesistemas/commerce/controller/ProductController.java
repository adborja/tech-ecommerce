package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product){
        try{
            return DefaultResponseBuilder.defaultResponse(productService.createProduct(product), HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Status<?>> getProductById(@PathVariable String productId){
        try {
            Product product = productService.getProductById(productId);
            if(product != null){
                return DefaultResponseBuilder.defaultResponse(product, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/by-name")
    public ResponseEntity<Status<?>> getProductByName(@RequestParam String name){
        try {
            return DefaultResponseBuilder.defaultResponse(productService.getProductByName(name), HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
