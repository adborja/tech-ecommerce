package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {

    private final IProductService productService;

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "createProductFallback")
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
        log.info("Creating product...");
        try {
            Product created = this.productService.createProduct(product);
            addSelfLink(created);
            addLinks(created);
            log.info("The product was created successfully");
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.error("There was an error creating the product");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    @HystrixCommand(fallbackMethod = "getProductByIdFallback")
    public ResponseEntity<Status<?>> getProductById(@PathVariable String id) {
        log.info("Getting product by id...");
        try {
            Optional<Product> foundProduct = this.productService.findById(id);
            if (foundProduct.isPresent()) {
                Product newProduct = foundProduct.get();
                addSelfLink(newProduct);
                return DefaultResponseBuilder.defaultResponse(newProduct, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the product by id");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    @HystrixCommand(fallbackMethod = "getProductByNameFallback")
    public ResponseEntity<Status<?>> getProductByName(@PathParam("name") String name) {
        log.info("Getting product by name...");
        try {
            List<Product> products = this.productService.getProductByNamePrefix(name);
            if (products.size() > 0) {
                products.forEach(ProductController::addSelfLink);
                return DefaultResponseBuilder.defaultResponse(products, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the product by name");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/products/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @HystrixCommand(fallbackMethod = "updateProductFallback")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        log.info("Updating the product...");
        if (!StringUtils.isEmpty(product.getId())) {
            return DefaultResponseBuilder.defaultResponse("Bad request", HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<Product> updatedProduct = this.productService.updateProduct(id, product);

            if (updatedProduct.isPresent()) {
                Product newProduct = updatedProduct.get();
                addSelfLink(newProduct);
                log.info("the product was updated successfully");
                return DefaultResponseBuilder.defaultResponse(newProduct, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Couldn't update product", null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            log.error("There was an error updating the product");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    @HystrixCommand(fallbackMethod = "deleteProductFallback")
    public ResponseEntity<Status<?>> deleteProduct(@PathVariable String id) {
        log.info("Deleting the product...");
        try {
            boolean deleted = this.productService.deleteProduct(id);
            if (deleted) {
                log.info("The product was deleted successfully");
                return DefaultResponseBuilder.defaultResponse(true, HttpStatus.OK);
            }
            log.warn("The product couldn't be deleted");
            return DefaultResponseBuilder.defaultResponse(false, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error deleting the product");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> createProductFallback(@RequestBody Product product) {
        log.error("Creating product fallback");
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getProductByIdFallback(@PathVariable String id) {
        log.error("Get product by id fallback");
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> getProductByNameFallback(@PathParam("name") String name) {
        log.error("Get product by name fallback");
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> updateProductFallback(@PathVariable String id, @RequestBody Product product) {
        log.error("Update product fallback");
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private ResponseEntity<Status<?>> deleteProductFallback(@PathVariable String id) {
        log.error("Delete product fallback");
        Status<?> status = Status.builder()
                .message("Service unavailable. Please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    private static void addSelfLink(@NotNull final Product product) {
        Link selfLink = linkTo(methodOn(ProductController.class).getProductById(
                product.getId())).withSelfRel().withType("GET");
        product.add(selfLink);
    }

    private static void addLinks(@NotNull final Product product) {
        Link linkByName = linkTo(methodOn(ProductController.class).
                getProductByName(product.getName()))
                .withRel("by-name")
                .withType("GET");
        product.add(linkByName);

        Link linkUpdateProduct = linkTo(methodOn(ProductController.class)
                .updateProduct(product.getId(), product))
                .withRel("update")
                .withMedia(MediaType.APPLICATION_JSON_VALUE)
                .withType("PATH");
        product.add(linkUpdateProduct);

        Link deleteProduct = linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withRel("delete")
                .withType("DELETE");
        product.add(deleteProduct);

    }
}
