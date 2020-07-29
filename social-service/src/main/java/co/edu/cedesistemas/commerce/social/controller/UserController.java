package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.Store;
import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        try {
            User created = service.createUser(user.getId());
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        try {
            service.likeProduct(id, productId);
            String msg = "liked";
            return DefaultResponseBuilder.defaultResponse(msg, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {
        try {
            service.likeStore(id, storeId);
            String msg = "liked";
            return DefaultResponseBuilder.defaultResponse(msg, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
        try {
            service.rateStore(id, storeId,rate);
            String msg = "rateStore";
            return DefaultResponseBuilder.defaultResponse(msg, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
        try {
            service.addFriend(id, friendId);
            String msg = "friend added";
            return DefaultResponseBuilder.defaultResponse(msg, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteById(@PathVariable String id){
        service.deleteUser(id);
        return new ResponseEntity<>(Status.success(),HttpStatus.OK);

    }        


    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            User found = service.getById(id);
            if (found != null) {
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/stores/recommend")
    public ResponseEntity<Status<?>> recommendStores(@PathVariable String id, @RequestParam String zone,
                                                     @RequestParam String productType, @RequestParam Integer limit) {
        // TODO: Implement method here
        return null;
    }
}