package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUser(@PathVariable String id){
        User founded = service.getById(id);
        return DefaultResponseBuilder.defaultResponse(founded, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        User created = service.createUser(user);
        addSelfLink(created);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> activateUser(@PathVariable String id){
        User updated = service.activateUser(id);

        return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
    }

    private static void addSelfLink(@NotNull final User user){
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }
}
