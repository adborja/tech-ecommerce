package co.edu.cedesistemas.commerce.cart.controller;

import co.edu.cedesistemas.commerce.cart.model.Cart;
import co.edu.cedesistemas.commerce.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CartController.class)
public class CartControllerIT {
    @MockBean
    private CartService service;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testCreateCart() {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setCurrency("COP");
        cart.setTotal(10000F);

        Mono<Cart> monoCart = Mono.just(cart);

        Mockito.when(service.createCart(cart)).thenReturn(monoCart);

        webClient.post()
                .uri("/carts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(cart), Cart.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testAddItem() {
        Cart cart = new Cart();
        cart.setId(UUID.randomUUID().toString());
        cart.setCreatedAt(LocalDateTime.now());
        cart.setCurrency("COP");
        cart.setTotal(10000F);

        Mono<Cart> monoCart = Mono.just(cart);

        Cart.CartItem item = new Cart.CartItem();
        item.setId(UUID.randomUUID().toString());
        item.setName("the item");
        item.setCurrency("COP");
        item.setPrice(2000F);

        monoCart = monoCart.doOnNext(c -> c.addItem(item));

        Mockito.when(service.addItem(cart.getId(), item)).thenReturn(monoCart);

        webClient.put()
                .uri("/carts/" + cart.getId() + "/items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(item), Cart.CartItem.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(cart.getId())
                .jsonPath("$.items[0].id").isEqualTo(item.getId());
    }


    @Test
    void testGetTotalPrice() {
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
        Mono<Float> totalPrice = Mono.just(5000F);

        Mockito.when(service.getTotalPrice(cart.getId())).thenReturn(Mono.just(5000F));

        webClient.get()
                .uri("/carts/total/" + cart.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBody().equals(5000F);



    }
}
