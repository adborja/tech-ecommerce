package co.edu.cedesistemas.commerce.social.controller;

import co.edu.cedesistemas.commerce.social.model.User;
import co.edu.cedesistemas.commerce.social.repository.StoreRepository;
import co.edu.cedesistemas.commerce.social.service.StoreService;
import co.edu.cedesistemas.commerce.social.service.UserService;
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
    private final UserService service;
    private final StoreService storeService;

    @PostMapping("/users")
    public ResponseEntity<Status<?>> createUser(@RequestBody User user) {
        log.info("Creating user...");
        try {
            User created = this.service.createUser(user.getId());
            log.info("The user was created successfully");
            return DefaultResponseBuilder.defaultResponse(created, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("There was an error invoking createUser service");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/products/{productId}/like")
    public ResponseEntity<Status<?>> like(@PathVariable String id, @PathVariable String productId) {
        log.info("Linking a product...");
        try {
            this.service.likeProduct(id, productId);
            return DefaultResponseBuilder.defaultResponse("Product liked", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("There was an error liking a product");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/like")
    public ResponseEntity<Status<?>> storeLike(@PathVariable String id, @PathVariable String storeId) {
        log.info("Liking a store...");
        try {
            this.service.likeStore(id, storeId);
            return DefaultResponseBuilder.defaultResponse("Store liked", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("There was an error liking a store");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/stores/{storeId}/rate")
    public ResponseEntity<Status<?>> storeRate(@PathVariable String id, @PathVariable String storeId,
                                               @RequestParam float rate) {
        log.info("Rating a store");
        try {
            this.service.rateStore(id, storeId, rate);
            return DefaultResponseBuilder.defaultResponse("Store successfully rated", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("There was an error rating the store");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public ResponseEntity<Status<?>> addFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Adding friend...");
        try {
            this.service.addFriend(id, friendId);
            log.info("The friend was added correctly");
            return DefaultResponseBuilder.defaultResponse("Friend successfully added", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("There was an error adding a friend");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Status<?>> getUserById(@PathVariable String id) {
        log.info("Finding a user by id...");
        try {
            User user = this.service.getById(id);
            if (user != null) {
                return DefaultResponseBuilder.defaultResponse(user, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("User not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the user");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/stores/recommend")
    public ResponseEntity<Status<?>> recommendStores(@PathVariable String id, @RequestParam String zone,
                                                     @RequestParam String productType, @RequestParam Integer limit) {
        log.info("Recommending a store...");
        try {
            List<StoreRepository.StoreOccurrence> recommendedStores =
                    this.storeService.recommendStoresByZoneAndProductType(id, zone, productType, limit);
            if (recommendedStores.size() > 0) {
                return DefaultResponseBuilder.defaultResponse(recommendedStores, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Not found recommended stores", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error recommending the store");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/stores_by_products/recommend")
    public ResponseEntity<Status<?>> recommendStoresByProducts(@PathVariable String id, @RequestParam String zone,
                                                               @RequestParam String productType, @RequestParam Integer limit) {
        log.info("Recommending stores by products...");
        try {
            List<StoreRepository.StoreOccurrence> storeOccurrences = this.storeService.recommendStoreByProducts(id, zone,
                    productType, limit);

            if (storeOccurrences.size() > 0) {
                return DefaultResponseBuilder.defaultResponse(storeOccurrences, HttpStatus.OK);
            }
            return DefaultResponseBuilder.defaultResponse("Recommended stores by products not found", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting the recommended stores by products");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/users/{id}/stores_by_zone/recommend")
    public ResponseEntity<Status<?>> recommendStoresByZone(@PathVariable String id, @RequestParam String zone,
                                                           @RequestParam Integer limit) {
        log.info("Getting recommended stores by stores...");
        try {
            List<StoreRepository.StoreOccurrence> storeOccurrences = this.storeService.recommendStoresByZone(id,
                    zone, limit);
            if (storeOccurrences.size() > 0) {
                return DefaultResponseBuilder.defaultResponse(storeOccurrences, HttpStatus.OK);
            }
            return DefaultResponseBuilder.errorResponse("Recommended stores by zone not found", null, HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("There was an error getting recommended stores by zone");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Status<?>> deleteUser(@PathVariable String id) {
        log.info("Deleting user...");
        try {
            this.service.deleteUser(id);
            return DefaultResponseBuilder.defaultResponse("User deleted successfully", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("There was an error deleting the user");
            return DefaultResponseBuilder.errorResponse(ex.getMessage(), ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}