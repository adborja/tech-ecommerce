package co.edu.cedesistemas.commerce.cart.service;

import co.edu.cedesistemas.commerce.cart.model.Cart;
import co.edu.cedesistemas.commerce.cart.repository.CartRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return found.map(Cart::getItems) //found se suscribe a map
                .flatMapMany(Flux::fromIterable); //mono se suscribe flatmapmany
    }

    @Override
    public Mono<Cart> addItem(String id, Cart.CartItem item) {
        Mono<Cart> found = findById(id);
        found = found.doOnNext(c -> {
        	item.setId(UUID.randomUUID().toString());
        	c.addItem(item);
        }); //cuando el publicador found termina, retorna mono 
        return found.flatMap(repository::save); //found se suscribe a flatmap
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
    	//eliminar elemento por elemento
    	Mono<Cart> found = findById(id);
    	
    	found = found.doOnNext(c -> c.setItems(null));
    	found.flatMap(repository::save).subscribe();
    }

    @Override
    public Mono<Float> getTotalPrice(String cartId) {
        Flux<Cart.CartItem> found = getItems(cartId); //found suscrito a getItems
        
        Mono<Float> result = found.map(item -> item.getPrice()*item.getQuantity())
        		.reduce((float) 0, (a,b)-> a + b)
        		;
        
        return result;
    }

    private void removeItem(String cartId, Cart.CartItem item) {
        Mono<Cart> found = findById(cartId);
        found = found.doOnNext(c -> c.removeItem(item));
        found.flatMap(repository::save).subscribe();
    }
}
