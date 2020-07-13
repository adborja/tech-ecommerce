package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.service.IProductService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    public ResponseEntity<Status<?>> createProduct(@RequestBody Product product){
        try{
            Product productCreated = productService.createProduct(product);
            addSelfLink(productCreated);
            addLinks(productCreated);
            return DefaultResponseBuilder.defaultResponse(productCreated, HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Status<?>> getProductById(@PathVariable String productId){
        try {
            Product product = productService.getProductById(productId);
            if(product != null){
                addSelfLink(product);
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
            List<Product> productsFound = productService.getProductByName(name);
            productsFound.forEach(ProductController::addSelfLink);
            return DefaultResponseBuilder.defaultResponse(productsFound, HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Status<?>> updateProduct(@Valid @RequestBody Product product, @Valid @PathVariable String productId){
        try{
            Product productUpdated = productService.updateProduct(product, productId);
            if(productUpdated != null){
                addSelfLink(productUpdated);
                return DefaultResponseBuilder.defaultResponse(productUpdated, HttpStatus.OK);
            }else{
                return DefaultResponseBuilder.errorResponse("Product not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Status<?>> deleteProduct(@PathVariable String productId){
        try{
            productService.deleteProduct(productId);
            return DefaultResponseBuilder.defaultResponse(Optional.empty(), HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Product product) {
        Link selfLink = linkTo(methodOn(ProductController.class)
                .getProductById(product.getId()))
                .withSelfRel().withType("GET");
        product.add(selfLink);
    }

    private static void addLinks(@NotNull final Product product){
        Link deleteLink = linkTo(methodOn(ProductController.class)
                .deleteProduct(product.getId()))
                .withRel("delete")
                .withType("DELETE");
        product.add(deleteLink);

        Link updateLink = linkTo(methodOn(ProductController.class)
                .updateProduct(product, product.getId()))
                .withRel("update")
                .withMedia("application/json")
                .withType("PATCH");
        product.add(updateLink);

        Link byNameLink = linkTo(methodOn(ProductController.class)
                .getProductByName(product.getName()))
                .withRel("get")
                .withMedia("application/json")
                .withType("GET");
        product.add(byNameLink);
    }


}
