package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.*;
import co.edu.cedesistemas.common.SpringProfile;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Profile(SpringProfile.SANDBOX)
public class OrderServiceSandbox implements IOrderService {

    private final IAddressService addressServiceSandbox;
    private final IProductService productServiceSandbox;
    private final IUserService userServiceSandbox;
    private final IStoreService storeServiceSandbox;

    public Order createOrder(final Order order)  {
        order.setId(UUID.randomUUID().toString());
        order.setStatus(Order.Status.CREATED);
        return order;

    }

    public Order getById(final String id) throws Exception {
        Address addressSandbox = addressServiceSandbox.getById(UUID.randomUUID().toString());
        Product productSandbox = productServiceSandbox.getById(UUID.randomUUID().toString());
        User userSandbox = userServiceSandbox.getById(UUID.randomUUID().toString());
        Store storeSandbox = storeServiceSandbox.getById(UUID.randomUUID().toString());

        final OrderItem orderItem = new OrderItem();
        orderItem.setProductId(productSandbox.getId());
        orderItem.setFinalPrice(100.0F);
        orderItem.setQuantity(1);

        final Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setShippingAddressId(addressSandbox.getId());
        order.setStatus(Order.Status.CREATED);
        order.setStoreId(storeSandbox.getId());
        order.setUserId(userSandbox.getId());
        order.setItems(List.of(orderItem));

        return order;
    }
}
