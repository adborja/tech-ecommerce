package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user){
        try{
            User created = service.createUser(user);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id){
        try{
            User user = service.getById(id);
            if(user != null) return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email){
        try{
            List<User> users = service.getByEmail(email);
            return DefaultResponseBuilder.defaultResponse(users, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user){
        try{
            User updatedUser = service.updateUser(id, user);
            return DefaultResponseBuilder.defaultResponse(updatedUser, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id){
        try{
            service.deleteUser(id);
            return DefaultResponseBuilder.defaultResponse(new User(), HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
