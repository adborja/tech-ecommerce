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
        try {
            User created = service.createUser(user);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUser(@PathVariable String id) {
        try{
            Optional<User> user = service.getUser(id);
            if(user.isPresent()) {
                return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            }
            else {
                return DefaultResponseBuilder.errorResponse("user not found",null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email) {
        try {
            Optional<User> user = service.getUserbyEmail(email);
            if(user.isPresent()) {
                return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            }
            else {
                return DefaultResponseBuilder.errorResponse("user not found",null, HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User userUpdate) {

        try {
        Optional<User> user = service.getUser(id);
        if(user.isPresent()) {
            userUpdate.setId(id);
            userUpdate = service.updateUser(userUpdate);
            return DefaultResponseBuilder.defaultResponse(userUpdate, HttpStatus.OK);
        }
        else
            return DefaultResponseBuilder.errorResponse("user not found ",null, HttpStatus.NOT_FOUND);
        }
        catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        try {
                Optional<User> user = service.getUser(id);
                if (user.isPresent()) {
                    service.deleteUser(user.get());
                    return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
                } else
                    return DefaultResponseBuilder.errorResponse("user not found ", null, HttpStatus.NOT_FOUND);
            }
           catch (Exception ex) {
                return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }


}
