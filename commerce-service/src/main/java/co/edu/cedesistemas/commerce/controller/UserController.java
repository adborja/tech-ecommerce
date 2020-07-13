package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;



    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {

        User created = service.createUser(user);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUser(@PathVariable String id) {
        Optional<User> user = service.getUser(id);
        if(user.isPresent()) {
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        }
        else {
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email) {
        Optional<User> user = service.getUserbyEmail(email);
        return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id) {
        Optional<User> user = service.getUser(id);
         return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        Optional<User> user = service.getUser(id);
        if(user.isPresent()) {
            service.deleteUser(user.get());
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        }
        else
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.NOT_FOUND);

    }
}
