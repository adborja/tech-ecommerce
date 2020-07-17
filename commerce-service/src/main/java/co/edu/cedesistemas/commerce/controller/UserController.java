package co.edu.cedesistemas.commerce.controller;

import co.edu.cedesistemas.commerce.model.User;
import co.edu.cedesistemas.commerce.service.IUserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final IUserService service;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user){
        try{
            User created = service.createUser(user);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id){
        try{
            User user = service.getById(id);
            addSelfLink(user);
            addLinks(user);
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
            users.forEach(UserController::addSelfLink);
            users.forEach(UserController::addLinks);
            return DefaultResponseBuilder.defaultResponse(users, HttpStatus.OK);
        }catch (Exception e){
            return DefaultResponseBuilder.errorResponse(e.getMessage(), e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Status<?>> updateUser(@PathVariable String id, @RequestBody User user){
        try{
            User updatedUser = service.updateUser(id, user);
            addSelfLink(updatedUser);
            addLinks(updatedUser);
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

    private static void addSelfLink(User user){
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }

    private static void addLinks(User user){
        Link byEmailLink = linkTo(methodOn(UserController.class).getUserByEmail(user.getEmail()))
                .withRel("by-email").withType("GET");
        Link update = linkTo(methodOn(UserController.class).updateUser(user.getId(), user))
                .withRel("update").withType("PATCH");
        Link delete = linkTo(methodOn(UserController.class).deleteUser(user.getId()))
                .withRel("delete").withType("DELETE");
        user.add(byEmailLink, update, delete);
    }
}
