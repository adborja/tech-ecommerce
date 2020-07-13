package co.edu.cedesistemas.commerce.controller;


import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.commerce.service.ProductService;
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
import java.util.Optional;

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
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getProductsByName(@RequestParam String name) {
        try {
            List<Product> found = service.getByName(name);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Status<?>> getDeleteProductById(@PathVariable String id) {
        try {
            service.deleteProduct(id);
            String msg = "product deleted";
            return DefaultResponseBuilder.defaultResponse(msg, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            if (!id.equals(product.getId()) && product.getId() != null)
                return DefaultResponseBuilder.errorResponse("bad request", null, HttpStatus.BAD_REQUEST);

            Product updated = service.updateProduct(id, product);
            if (updated != null) return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        }
    }

    private static void addSelfLink(@NotNull final Product product) {
        Link selfLink = linkTo(methodOn(StoreController.class)
                .getStoreById(product.getId()))
                .withSelfRel().withType("GET");
        product.add(selfLink);

        Link selfDelLink = linkTo(methodOn(ProductController.class)
                .getDeleteProductById(product.getId()))
                .withSelfRel().withType("DELETE");
        product.add(selfDelLink);

    }

    private static void addLinks(@NotNull final Product product) {

        Link byNameLink = linkTo(methodOn(ProductController.class)
                .getProductsByName(product.getName()))
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
