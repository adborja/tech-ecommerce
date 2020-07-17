package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public Order getById(String id) {
        Store store = new Store();
        store.setId(id);
        store.setCreatedAt(LocalDateTime.now());
        store.setType(Store.Type.TECHNOLOGY);
        store.setPhone("+5744444444");
        store.setName("test store");
        store.setAddress("123 Fake Street.");

        return Order.builder()
                .id(id)
                .items(null)
                .shippingAddress(Address.builder()
                        .id(id)
                        .name("Avenue 80")
                        .description("Near to CC Molinos")
                        .city("Medellin")
                        .countryISOCode("NA")
                        .regionISOCode("someplace")
                        .phoneNumber("0000")
                        .street1("calle 81 a").build())
                .status(Order.Status.CREATED)
                .user(User.builder().id(UUID.randomUUID().toString()).name("Jose").lastName("Herrera").email("prueba@prueba.com").build())
                .store(store)
                .createdAt(LocalDateTime.now()).build();
    }

    @Override
    public List<OrderItem> getOrderItems(String id) {
        List<OrderItem> items = new ArrayList<>();
        Product p1 = Product.builder()
                .description("Pestañina a prueba de agua")
                .name("Pestañina ref100")
                .id(UUID.randomUUID().toString()).build();
        Product p2 = Product.builder()
                .description("Esmalte")
                .name("Esmalte blanco novia ref400")
                .id(UUID.randomUUID().toString()).build();
        OrderItem item1 = OrderItem.builder().finalPrice(4500.1F).product(p1).quantity(1).build();
        OrderItem item2 = OrderItem.builder().finalPrice(100.0F).product(p1).quantity(10).build();
        items.add(item1);
        items.add(item2);
        return items;
    }
}
