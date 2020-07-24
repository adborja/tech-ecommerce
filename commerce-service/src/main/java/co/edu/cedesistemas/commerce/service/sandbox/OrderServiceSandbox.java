package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.*;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@AllArgsConstructor
@Service
public class OrderServiceSandbox implements IOrderService {

    private final IAddressService addressService;
    private final IProductService productService;
    private final IUserService userService;
    private final IStoreService storeService;

    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        return  order;
    }

    @Override
    public Order getById(String id) throws Exception {

        Address addressCreated = addressService.getById(UUID.randomUUID().toString());
        Product productCreated = productService.getById(UUID.randomUUID().toString());
        User userCreated = userService.getById(UUID.randomUUID().toString());
        Store storeCreated = storeService.getById(UUID.randomUUID().toString());

        // Product
/*        Product p = new Product();
        p.setId(UUID.randomUUID().toString());
        p.setName("name Product");
        p.setDescription("description Product");*/
        // OrderItems
        final OrderItem orderItem = new OrderItem();
        orderItem.setProductId(UUID.randomUUID().toString());
        orderItem.setFinalPrice(123.2F);
        orderItem.setQuantity(12);
        // User
        /*final User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail("123@store.com");
        user.setName("fake name");
        user.setLastName("fake lasname");
        // Store
        final Store store = new Store();
        store.setId(UUID.randomUUID().toString());
        store.setName("face name store");


        store.setPhone("123-123-123");
        store.setAddress("fake address");
        store.setType(Store.Type.TECHNOLOGY);
        store.setCreatedAt(LocalDateTime.now());*/
        //Address


        return Order.builder()
                .id(id)
                .userId(UUID.randomUUID().toString())
                .storeId(UUID.randomUUID().toString())
                .shippingAddressId(UUID.randomUUID().toString())
                .status(Order.Status.CREATED)
                .createdAt(LocalDateTime.now())
                .items(List.of(orderItem))
                .build();

    }

    @Override
    public List<OrderItem> getOrderItems(String id) {
        return null;
    }
}
