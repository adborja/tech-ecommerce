package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {
    @Override
    public Order createOrder(Order order) {
        order.setStatus(Order.Status.CREATED);
        return order;
    }

    @Override
    public List<OrderItem> getOrderItemsById(String id) {
        List<OrderItem> items = new ArrayList<>();
        Product p1 = Product.builder()
                .description("Same Producto")
                .id(UUID.randomUUID().toString())
                .name("p1").build();
        Product p2 = Product.builder()
                .description("Other Producto")
                .id(UUID.randomUUID().toString())
                .name("p2").build();
        OrderItem item1 = OrderItem.builder()
                .finalPrice(1213.1F)
                .productId(p1.getId())
                .quantity(2).build();
        OrderItem item2 = OrderItem.builder()
                .finalPrice(5002.1F)
                .productId(p2.getId())
                .quantity(3).build();
        items.add(item1);
        items.add(item2);
        return items;
    }

    @Override
    public Order getOrderById(String id) {
        Store store = new Store();
        store.setId(id);
        store.setCreatedAt(LocalDateTime.now());
        store.setType(Store.Type.TECHNOLOGY);
        store.setPhone("+5744444444");
        store.setName("test store");
        store.setAddress("123 Fake Street.");

        Address address = Address.builder()
                .city("Ciudad Falsa")
                .id(id)
                .name("Nombre Falso")
                .description("Description Falso")
                .countryISOCode("ISO Falso")
                .phoneNumber("2345678765")
                .regionISOCode("ISO Region Falso")
                .street1("Cra Falsa").build();

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email("correofalso@falso.com")
                .lastName("Falsa")
                .name("Persona")
                .build();
        return Order.builder()
                .id(id)
                .items(null)
                .shippingAddressId(address.getId())
                .status(Order.Status.CANCELLED)
                .userId(user.getId())
                .storeId(store.getId())
                .createdAt(LocalDateTime.now()).build();
    }
}
