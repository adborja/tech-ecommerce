package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.ProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
public class ProductController {

    private ProductService service;

    @GetMapping("/products/{id}")
    public ResponseEntity<Status<?>> getProductById(@PathVariable String id) {
        try {
            Product found = service.getById(id);
            addSelfLink(found);
            addLinks(found);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("Product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getProductsByName(@RequestParam String name) {
        try {
            List<Product> found = service.getProductsByName(name);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("Cannot find products by name" + name, null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateProductById(@PathVariable String id) {
        try {
            Product found = service.updateProductById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("Product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Status<?>> deleteProductById(@PathVariable String id) {
        try {
            Product found = service.deleteProductById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("Product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product) {
        try {
            Product created = service.createProduct(product);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
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
        Link byTypeLink = linkTo(methodOn(ProductController.class)
                .getProductsByName(product.getName()))
                .withRel("by-name")
                .withType("GET");
        product.add(byTypeLink);

        Link byNameLink = linkTo(methodOn(ProductController.class)
                .updateProductById(product.getId()))
                .withRel("update")
                .withMedia("application/json")
                .withType("PATCH");
        product.add(byNameLink);

        Link update = linkTo(methodOn(ProductController.class)
                .deleteProductById(product.getId()))
                .withRel("delete")
                .withMedia("application/json")
                .withType("DELETE");
        product.add(update);
    }


}
