package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
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

    private final UserService service;


    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {

        User created = service.createUser(user);
        addSelfLink(created);

        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);

    }

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getByIdFallback")
    public ResponseEntity<Status<?>> getUser(@PathVariable String id) {
        Optional<User> user = service.getUser(id);
        if (user.isPresent()) {
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        } else {
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/by-email")
    public ResponseEntity<Status<?>> getUserByEmail(@RequestParam String email) {
        Optional<User> user = service.getUserbyEmail(email);
        return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id) {
        User user = service.activateUser(id);
        if(user != null) {
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        }
        else
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        Optional<User> user = service.getUser(id);
        if (user.isPresent()) {
            service.deleteUser(user.get());
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        } else
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.NOT_FOUND);

    }


    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }

    private ResponseEntity<Status<?>> getByIdFallback(final String id) {
        log.error("getting user by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
