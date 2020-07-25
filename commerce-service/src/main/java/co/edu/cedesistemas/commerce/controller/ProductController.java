package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {

    IProductService service;

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product){
        try{
            Product created = service.createProduct(product);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/products/{id}")
    @HystrixCommand(fallbackMethod = "getFallback")
    public  ResponseEntity<Status<?>> getProduct(@PathVariable String id){
        try{
            Product found = service.getProductById(id);
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.defaultResponse("product not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    @HystrixCommand(fallbackMethod = "getFallback")
    public  ResponseEntity<Status<?>> getProductByName(@RequestParam String name){
        try{
            List<Product> found = service.getProductByName(name);
            found.forEach(ProductController::addSelfLink);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> getFallback(final String id) {
        log.error("getting products by id or name fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @DeleteMapping("/products/{id}")
    public  ResponseEntity<Status<?>> deleteProduct(@PathVariable String id){
        try{
            Product found = service.deleteProductById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("product not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product){
        try{
            if (product.getId() != null) return DefaultResponseBuilder.defaultResponse("Operation invalid", HttpStatus.BAD_REQUEST);
            Product found = service.updateProductById(id,product);
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.defaultResponse("product not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Product product) {
        Link selfLink = linkTo(methodOn(ProductController.class)
                .getProduct(product.getId()))
                .withSelfRel().withType("GET");
        product.add(selfLink);
    }

    private static void addLinks(@NotNull final Product product) {
        Link delete = linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withRel("delete")
                .withType("DELETE");
        product.add(delete);

        Link byNameLink = linkTo(methodOn(ProductController.class)
                .getProductByName(product.getName()))
                .withRel("by-name")
                .withType("GET");
        product.add(byNameLink);

        Link update = linkTo(methodOn(ProductController.class)
                .updateProduct(product.getId(), product))
                .withRel("update")
                .withMedia("application/json")
                .withType("PATCH");
        product.add(update);
    }
}
