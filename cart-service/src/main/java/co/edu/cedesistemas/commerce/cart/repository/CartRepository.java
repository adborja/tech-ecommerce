package co.edu.cedesistemas.commerce.cart.repository;

import co.edu.cedesistemas.commerce.cart.model.Cart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CartRepository extends ReactiveMongoRepository<Cart, String> {
}
