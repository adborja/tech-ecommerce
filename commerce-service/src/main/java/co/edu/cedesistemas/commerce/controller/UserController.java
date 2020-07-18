package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.commerce.service.ProductService;
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
    private final IUserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        try {
            User created = service.createUser(user);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        try {
            User found = service.getById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.errorResponse("product not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByName(@RequestParam String email) {
        try {
            List<User> found = service.getByEmail(email);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUserById(@PathVariable String id) {
        try {
            service.deleteUserById(id);
            return DefaultResponseBuilder.defaultResponse("User Deleted",HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            if (!id.equalsIgnoreCase(user.getId()) && user.getId() != null){
                return DefaultResponseBuilder.errorResponse("Bad Request",null,HttpStatus.BAD_REQUEST);
            }else {
                User updatedUser = service.updateUser(id, user);
                if (updatedUser != null)
                    return DefaultResponseBuilder.defaultResponse(updatedUser, HttpStatus.OK);
                else
                    return DefaultResponseBuilder.errorResponse("user not found", null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        }
    }


}