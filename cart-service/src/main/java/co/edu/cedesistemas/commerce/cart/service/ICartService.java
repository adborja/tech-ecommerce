package co.edu.cedesistemas.commerce.cart.service;

import co.edu.cedesistemas.commerce.cart.model.Cart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICartService {
    Mono<Cart> createCart(Cart cart);
    Mono<Cart> findById(String id);
    Flux<Cart.CartItem> getItems(String cartId);
    Mono<Cart> addItem(String id, Cart.CartItem item);
    Mono<Void> delete(String id);
    void empty(String id);
    Mono<Cart> removeItem(String cartId, String itemId);
    Mono<Float> getTotalPrice(String cartId);
}
