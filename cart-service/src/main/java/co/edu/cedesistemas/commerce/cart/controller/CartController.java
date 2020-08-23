package co.edu.cedesistemas.commerce.cart.controller;

import co.edu.cedesistemas.commerce.cart.model.Cart;
import co.edu.cedesistemas.commerce.cart.service.CartService;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class CartController {
    private final CartService service;

    @PostMapping("/carts")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cart> createCart(@RequestBody Cart cart) {
        return service.createCart(cart);
    }

    @GetMapping("/carts/{id}")
    public Mono<Cart> getById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping("/carts/{id}/items")
    public Flux<Cart.CartItem> getCartItems(@PathVariable String id) {
        return service.getItems(id);
    }

    @PutMapping("/carts/{id}/items")
    public Mono<Cart> addItem(@PathVariable String id, @RequestBody Cart.CartItem item) {
        return service.addItem(id, item);
    }

    @GetMapping("/carts/{id}/total")
    public Mono<Float> getTotalPrice(@PathVariable String id){
        return service.getTotalPrice(id);
    }

    @PutMapping("/carts/{id}/empty")
    public Mono<Status<?>> empty(@PathVariable String id) {
        service.empty(id);
        Status<?> status = Status.builder()
                ._hits(1)
                ._source(Status.class.getName())
                .code(HttpStatus.OK.value())
                .message(String.format("cart %s empty", id))
                .build();
        return Mono.just(status);
    }

    @DeleteMapping("/carts/{id}/items/{itemId}")
    public Mono<Cart> removeItem(@PathVariable String id, @PathVariable String itemId) {
        return service.removeItem(id, itemId);
    }
}
