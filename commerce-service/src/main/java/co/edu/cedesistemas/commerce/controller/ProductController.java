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

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final IProductService service;

    @PostMapping("/products")
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product){
        try{
            Product created = service.createProduct(product);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Status<?>> getProduct(@PathVariable String id){
        try {
            Product product = service.getById(id);
            addSelfLink(product);
            addLinks(product);
            if(product == null) return DefaultResponseBuilder.errorResponse(
                    "producto no encontrado", null, HttpStatus.NOT_FOUND);
            return DefaultResponseBuilder.defaultResponse(product, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/by-name")
    public ResponseEntity<Status<?>> getProductByName(@RequestParam String name){
        try{
            List<Product> products = service.getByName(name);
            products.forEach(ProductController::addSelfLink);
            products.forEach(ProductController::addLinks);
            return DefaultResponseBuilder.defaultResponse(products, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<Status<?>> updateProduct(@PathVariable String id, @RequestBody Product product){
        try{
            if(product.getId() != null || !product.getId().isEmpty()){
                return DefaultResponseBuilder.errorResponse(
                        "No se puede actualizar id", null, HttpStatus.BAD_REQUEST);
            }
            Product updated = service.updateProduct(id, product);
            addSelfLink(updated);
            addLinks(updated);
            return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Status<?>> deleteProduct(@PathVariable String id){
        try{
            service.deleteProduct(id);
            return DefaultResponseBuilder.defaultResponse(new Product(), HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(Product product){
        Link selfLink = linkTo(methodOn(ProductController.class).getProduct(product.getId()))
                .withSelfRel().withType("GET");
        product.add(selfLink);
    }

    private static void addLinks(Product product){
        Link deleteLink = linkTo(methodOn(ProductController.class).deleteProduct(product.getId()))
                .withRel("Delete").withType("DELETE");
        Link updateLink = linkTo(methodOn(ProductController.class).updateProduct(product.getId(), product))
                .withRel("Update").withType("PATCH");
        Link byNameLink = linkTo(methodOn(ProductController.class).getProductByName(product.getName()))
                .withRel("by-name").withType("GET");

        product.add(deleteLink, updateLink, byNameLink);
    }
}
