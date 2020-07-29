package co.edu.cedesistemas.commerce.registration.controller;

import co.edu.cedesistemas.commerce.registration.model.User;
import co.edu.cedesistemas.commerce.registration.services.EventPublisherService;
import co.edu.cedesistemas.commerce.registration.services.UserService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.event.RegistrationEvent;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final EventPublisherService publisherService;

    @PostMapping
    public ResponseEntity<Status<?>> createUser(@RequestBody User user){
        try{
            User userCreated = userService.createUser(user);
            addSelfLink(userCreated);
            return DefaultResponseBuilder.defaultResponse(userCreated, HttpStatus.CREATED);
        }catch (Exception ex){
            log.error(ex.getMessage());
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String userId){
        try{
            User userFound = userService.getUserById(userId);
            if(userFound != null){
                return DefaultResponseBuilder.defaultResponse(userFound, HttpStatus.OK);
            }else {
                return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{userId}/activate")
    public ResponseEntity<Status<?>> activateUser(@PathVariable String userId){
        try{
            User user = userService.activateUser(userId);
            if(user != null){
                return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            }else{
                return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String userId){
        try{
            userService.deleteUser(userId);
            return DefaultResponseBuilder.defaultResponse("User Deleted", HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUserById(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }
}
