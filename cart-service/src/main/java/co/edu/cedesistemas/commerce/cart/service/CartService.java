package co.edu.cedesistemas.commerce.cart.service;

import co.edu.cedesistemas.commerce.cart.model.Cart;
import co.edu.cedesistemas.commerce.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CartService implements ICartService {
    private final CartRepository repository;

    @Override
    public Mono<Cart> createCart(Cart cart) {
        cart.setId(UUID.randomUUID().toString());
        cart.setCreatedAt(LocalDateTime.now());
        return repository.save(cart);
    }

    @Override
    public Mono<Cart> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Cart.CartItem> getItems(String cartId) {
        Mono<Cart> found = findById(cartId);
        return found.map(Cart::getItems)
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Cart> addItem(String id, Cart.CartItem item) {
        Mono<Cart> found = findById(id);
        found = found.doOnNext(c -> c.addItem(item));
        return found.flatMap(repository::save);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Cart> removeItem(String cartId, String itemId) {
        Mono<Cart> found = findById(cartId);
        Mono<Cart.CartItem> item = getItems(cartId)
                .filter(i -> i.getId().equals(itemId))
                .singleOrEmpty();
        return found.doOnNext(c -> removeItem(cartId, item.block()));
    }

    @Override
    public void empty(String id) {

         getItems(id)
                 .doOnNext(cartItem -> removeItem(id,cartItem.getId()));

    }

    @Override
    public Mono<Float> getTotalPrice(String cartId) {

        return getItems(cartId)
                .map(Cart.CartItem::getPrice)
                .reduce(Float::sum);



    }

    private void removeItem(String cartId, Cart.CartItem item) {
        Mono<Cart> found = findById(cartId);
        found = found.doOnNext(c -> c.removeItem(item));
        found.flatMap(repository::save).subscribe();
    }





}
