package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.service.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService service;

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id){
        User found = service.getUserById(id);
        if(found == null) return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
        else return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> activateUserById(@PathVariable String id){
        User found = service.activateUserById(id);
        if(found.getId() == null) return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
        else return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(User user){
        User created = service.createUser(user);
        created.add(linkTo(methodOn(UserController.class)
                .getUserById(user.getId()))
                .withSelfRel().withType("GET"));
        if(created.getId() == null) return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
        else return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUserById(user.getId()))
                .withSelfRel()
                .withType("GET");
        user.add(selfLink);
    }


}
