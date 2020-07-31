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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {

    UserService service;

    @GetMapping("/users/{id}")
    @HystrixCommand(fallbackMethod = "getFallback")
    public ResponseEntity<Status<?>> getUser(@PathVariable String id){
        try{
            User user = service.getUser(id);
            addSelfLink(user);
            addLinks(user);
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("error",ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Status<?>> getFallback(final String id) {
        log.error("getting users by id fallback {}", id);
        Status<?> status = Status.builder()
                ._hits(1)
                .message("service unavailable. please try again")
                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                .build();
        return new ResponseEntity<>(status, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @PutMapping("/users/{id}/activate")
    public ResponseEntity<Status<?>> activateUser(@PathVariable String id){
        try{
            return DefaultResponseBuilder.defaultResponse(service.activateUser(id), HttpStatus.OK);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse(ex.getMessage(),ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user){
        try{
            user = service.createUser(user);
            addSelfLink(user);
            addLinks(user);
            return DefaultResponseBuilder.defaultResponse(user, HttpStatus.CREATED);
        }catch (Exception ex){
            return DefaultResponseBuilder.errorResponse("error",ex,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id){
        service.deleteUser(id);
        return DefaultResponseBuilder.defaultResponse(Status.success(),HttpStatus.OK);

    }


    private static void addSelfLink(@NotNull final User user) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .getUser(user.getId()))
                .withSelfRel().withType("GET");
        user.add(selfLink);
    }

    private static void addLinks(@NotNull final User user) {
        Link delete = linkTo(methodOn(UserController.class)
                .activateUser(user.getId()))
                .withRel("activate")
                .withType("GET");
        user.add(delete);
    }
}
