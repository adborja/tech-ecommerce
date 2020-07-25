package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.commerce.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        }
        catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getByIdFallback")
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
    @HystrixCommand(fallbackMethod = "getByIdFallback")
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

    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }

    private static void addLinks(@NotNull final User user) {
        Link byNameLink = linkTo(methodOn(UserController.class)
                .getUserByEmail(user.getEmail()))
                .withRel("by-email")
                .withType("GET");
        user.add(byNameLink);
        Link update = linkTo(methodOn(UserController.class)
                .updateUser(user.getId(), user))
                .withRel("update")
                .withMedia("application/json")
                .withType("PATCH");
        user.add(update);
        Link delete = linkTo(methodOn(UserController.class)
                .deleteUser(user.getId()))
                .withSelfRel().withType("DELETE");
        user.add(delete);


    }

    private ResponseEntity<Status<?>> getByIdFallback(final String id) {
        log.error("getting store by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
