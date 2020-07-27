package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
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
    private final IProductService service;

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
        try {
            Product created = service.createProduct(product);
            addSelfLink(created);
            addLinks(created);
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
            else return DefaultResponseBuilder.defaultResponse("product not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getProductByName(@RequestParam String name) {
        try {
            List<Product> found = service.getByName(name);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product) {
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
    public ResponseEntity<Status<?>> deleteProduct(@PathVariable String id) {
        try {
            service.deleteProduct(id);
            return DefaultResponseBuilder.defaultResponse("delete product id: " + id, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Product product) {
        Link selfLink = linkTo(methodOn(ProductController.class)
                .getProductById(product.getId()))
                .withSelfRel().withType("GET");
        product.add(selfLink);
    }

    private static void addLinks(@NotNull final Product product) {
        Link deleteLink = linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withRel("delete")
                .withType("DELETE");
        product.add(deleteLink);

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