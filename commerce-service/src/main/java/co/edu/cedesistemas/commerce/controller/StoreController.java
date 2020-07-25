package co.edu.cedesistemas.commerce.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.IStoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@AllArgsConstructor
@Slf4j
public class StoreController {
    private final IStoreService service;

    @PostMapping("/stores")
    public ResponseEntity<Status<?>> createStore(@RequestBody Store store) {
        try {
            Store created = service.createStore(store);
            addSelfLink(created);
            addLinks(created);
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/{id}")
    @HystrixCommand(fallbackMethod = "getByIdFallback")
    public ResponseEntity<Status<?>> getStoreById(@PathVariable String id) {
        try {
            Store found = service.getById(id);
            if (found != null) {
                addSelfLink(found);
                return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/by-name")
    @HystrixCommand(fallbackMethod = "getByIdFallback")
    public ResponseEntity<Status<?>> getStoresByName(@RequestParam String name) {
        try {
            List<Store> found = service.getByName(name);
            found.forEach(StoreController::addSelfLink);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stores/by-type")
    @HystrixCommand(fallbackMethod = "getByIdFallback")
    public ResponseEntity<Status<?>> getStoresByType(@RequestParam Store.Type type) {
        try {
            List<Store> found = service.getByType(type);
            found.forEach(StoreController::addSelfLink);
            return DefaultResponseBuilder.defaultResponse(found, HttpStatus.OK);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/stores/{id}")
    public ResponseEntity<Status<?>> updateStore(@PathVariable String id, @RequestBody Store store) {
        try {
            Store updated = service.updateStore(id, store);
            if (updated != null) {
                addSelfLink(updated);
                return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.OK);
            } else return DefaultResponseBuilder.errorResponse("store not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static void addSelfLink(@NotNull final Store store) {
        Link selfLink = linkTo(methodOn(StoreController.class)
                .getStoreById(store.getId()))
                .withSelfRel().withType("GET");
        store.add(selfLink);
    }

    private static void addLinks(@NotNull final Store store) {
        Link byTypeLink = linkTo(methodOn(StoreController.class)
                .getStoresByType(store.getType()))
                .withRel("by-type")
                .withType("GET");
        store.add(byTypeLink);

        Link byNameLink = linkTo(methodOn(StoreController.class)
                .getStoresByName(store.getName()))
                .withRel("by-name")
                .withType("GET");
        store.add(byNameLink);

        Link update = linkTo(methodOn(StoreController.class)
                .updateStore(store.getId(), store))
                .withRel("update")
                .withMedia("application/json")
                .withType("PATCH");
        store.add(update);
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