package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
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
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final IProductService service;

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
    @HystrixCommand(fallbackMethod = "getByIdFallback")
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
    @HystrixCommand(fallbackMethod = "getByIdFallback")
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

     private static void addSelfLink(@NotNull final Product product) {
        Link selfLink = linkTo(methodOn(StoreController.class)
                .getStoreById(product.getId()))
                .withSelfRel().withType("GET");
        product.add(selfLink);

    }
    private static void addLinks(@NotNull final Product product) {
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
        Link delete = linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withSelfRel().withType("DELETE");
        product.add(delete);
    }

    private ResponseEntity<Status<?>> getByIdFallback(final String id) {
        log.error("getting store by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
