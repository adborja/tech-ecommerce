package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class OrderServiceSandbox implements IOrderService {
    @Override
    public Order createOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(Order.Status.CREATED);
        return order;
    }

    @Override
    public Order getById(String id) {
        Store store1 = new Store();
        store1.setId(UUID.randomUUID().toString());
        store1.setCreatedAt(LocalDateTime.now());
        store1.setType(Store.Type.TECHNOLOGY);
        store1.setPhone("+5744444444");
        store1.setName("test store");
        store1.setAddress("123 Fake Street.");

        Address address = Address.builder()
                .id(UUID.randomUUID().toString())
                .city("Bogotá")
                .countryISOCode("CO")
                .regionISOCode("CUN")
                .description("pier30")
                .name("work")
                .phoneNumber("03457345878")
                .street1("calle 34 # 423 - 45")
                .street2("apto. 4")
                .zip("50012")
                .build();

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .name("Pedro")
                .lastName("Navajas")
                .email("Eoeoeo@gmail.com")
                .build();

        List<OrderItem> orderItemList = Arrays.asList(
                OrderItem.builder()
                        .product(Product.builder().id(UUID.randomUUID().toString()).name("Product1").description("Hard Drive").build())
                        .quantity(2)
                        .finalPrice(10000F)
                        .build(),

                OrderItem.builder()
                        .product(Product.builder().id(UUID.randomUUID().toString()).name("Product2").description("Hard Drive SDD").build())
                        .quantity(4)
                        .finalPrice(550000F)
                        .build());


        return Order.builder()
                .id(id)
                .createdAt(LocalDateTime.now().minus(40, ChronoUnit.MINUTES))
                .shippingAddress(address)
                .status(Order.Status.CREATED)
                .store(store1)
                .user(user)
                .items(orderItemList)
                .build();
    }
}
