package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
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
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@Slf4j
public class UserController {

     IUserService service;

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getFallback")
    public ResponseEntity<Status<?>> getUser(@PathVariable String id){
        try{
            User found = service.getUserById(id);
            addLinks(found);
            addSelfLink(found);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        }catch (NoSuchElementException ex){
            return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/by-email")
    @HystrixCommand(fallbackMethod = "getFallback")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email){
        try{
            List<User> found = service.getUserByEmail(email);
            found.forEach(UserController::addSelfLink);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user){
        try{
            if (user.getId() != null) return DefaultResponseBuilder.defaultResponse("Operation invalid", HttpStatus.BAD_REQUEST);
            User found = service.updateUserById(id,user);
            if (found != null) {
                addLinks(found);
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            }else return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id){
        try{
            User found = service.deleteUserById(id);
            if (found != null) return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            else return DefaultResponseBuilder.defaultResponse("user not found", HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user){
        try{
            User created = service.createUser(user);
            addLinks(created);
            addSelfLink(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> getFallback(final String id) {
        log.error("getting users by id or email fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }


    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }

    private static void addLinks(@NotNull final User user) {
        Link delete = linkTo(methodOn(UserController.class)
                .deleteUser(user.getId()))
                .withRel("delete")
                .withType("DELETE");
        user.add(delete);

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
    }
}
