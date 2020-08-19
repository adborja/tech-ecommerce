package co.edu.cedesistemas.commerce.cart.service;

import co.edu.cedesistemas.commerce.cart.controller.CartController;
import co.edu.cedesistemas.commerce.cart.model.Cart;
import co.edu.cedesistemas.commerce.cart.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CartService.class)
public class CartServiceIT {

    @MockBean
    private  CartRepository repository;
    @Autowired
    private CartService service;


    @Test
    public void getTotalPriceSucces(){

        Set<Cart.CartItem> items = new HashSet<>();
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setCurrency("COP");
        cart.setTotal(10000F);

        Cart.CartItem item1 = new Cart.CartItem();
        item1.setId(UUID.randomUUID().toString());
        item1.setName("the item");
        item1.setCurrency("COP");
        item1.setPrice(2000F);

        Cart.CartItem item2 = new Cart.CartItem();
        item2.setId(UUID.randomUUID().toString());
        item2.setName("the item");
        item2.setCurrency("COP");
        item2.setPrice(3000F);
        cart.addItem(item1);
        cart.addItem(item2);

        Flux<Cart.CartItem> cartItemFlux= Flux.fromIterable(cart.getItems());
        Mockito.when(repository.findById(cart.getId())).thenReturn(Mono.just(cart));

        assertThat(service.getTotalPrice(cart.getId()).block(), equalTo((5000F)));



    }
}
