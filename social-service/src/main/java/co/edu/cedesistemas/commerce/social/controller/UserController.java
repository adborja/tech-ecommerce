package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        User user1 = service.createUser(user.getId());
        return DefaultResponseBuilder.defaultResponse(user1, HttpStatus.OK);
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        try {
            service.likeProduct(id, productId);
            return DefaultResponseBuilder.defaultResponse("product like",HttpStatus.OK);
        } catch (Exception e) {
            DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {

        try {
            service.likeStore(id, storeId);
            return DefaultResponseBuilder.defaultResponse("",HttpStatus.OK);
        } catch (Exception e) {
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
        try {
            service.rateStore(id, storeId,rate);
            return DefaultResponseBuilder.defaultResponse("",HttpStatus.OK);
        } catch (Exception e) {
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
        try {
            service.addFriend(id, friendId);
            return DefaultResponseBuilder.defaultResponse("",HttpStatus.OK);
        } catch (Exception e) {
            return DefaultResponseBuilder.errorResponse(e.getMessage(),e,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        // TODO: Implement method here
        return null;
    }

    @GetMapping("/users/{id}/stores/recommend")
    public ResponseEntity<Status<?>> recommendStores(@PathVariable String id, @RequestParam String zone,
                                                     @RequestParam String productType, @RequestParam Integer limit) {
        // TODO: Implement method here
        return null;
    }
}