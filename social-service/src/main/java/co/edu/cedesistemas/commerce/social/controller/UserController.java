package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        User created = service.createUser(user.getId());
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        // TODO: Implement method here
        return null;
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {
        // TODO: Implement method here
        return null;
    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
        // TODO: Implement method here
        return null;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
        // TODO: Implement method here
        return null;
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

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteById(@PathVariable String id) {
        service.deleteUser(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }
}