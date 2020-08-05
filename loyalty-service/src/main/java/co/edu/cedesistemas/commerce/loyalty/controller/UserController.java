package co.edu.cedesistemas.commerce.loyalty.controller;

import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import co.edu.cedesistemas.commerce.loyalty.service.UserOrderService;
import co.edu.cedesistemas.commerce.loyalty.service.UserStoreService;
import co.edu.cedesistemas.common.DefaultResponseBuilder;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserStoreService userStoreService;
    private final UserOrderService userOrderService;

    @PostMapping("/user-stores")
    public ResponseEntity<Status<?>> createUserStore(@RequestBody UserStore userStore) {
        UserStore created = userStoreService.createUserStore(userStore);
        return DefaultResponseBuilder.defaultResponse(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/user-stores/{id}")
    public ResponseEntity<Status<?>> deleteUserStore(@PathVariable String id) {
        userStoreService.deleteUserStore(id);
        return new ResponseEntity<>(Status.success(), HttpStatus.OK);
    }

    @PutMapping("/user-order")
    public ResponseEntity<Status<?>> registerUserStore(@PathVariable UserOrder userOrder) {
        UserOrder updated = userOrderService.registerOrder(userOrder.getId(),userOrder.getStoreId(),userOrder.getUserId(),userOrder.getOrderValue());
        return DefaultResponseBuilder.defaultResponse(updated, HttpStatus.CREATED);
    }

}
