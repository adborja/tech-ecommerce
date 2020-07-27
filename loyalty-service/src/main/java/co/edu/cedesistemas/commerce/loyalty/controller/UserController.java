package co.edu.cedesistemas.commerce.loyalty.controller;

import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import co.edu.cedesistemas.commerce.loyalty.service.UserStoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserStoreService userStoreService;

    @PostMapping("/users/stores")
    public ResponseEntity<Status<?>> createUserStore(@RequestBody UserStore userStore) {
        UserStore created = userStoreService.createUserStore(userStore);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }
}
