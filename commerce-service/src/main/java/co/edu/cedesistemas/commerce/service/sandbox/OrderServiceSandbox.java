package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.*;
import co.edu.cedesistemas.commerce.service.IOrderService;
import co.edu.cedesistemas.common.SpringProfile;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
    public Order getOrderById(String orderId) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(RandomStringUtils.randomAlphanumeric(5) + "@company.com")
                .name(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .build();

        Store store = Store.builder()
                .id(UUID.randomUUID().toString())
                .name(RandomStringUtils.randomAlphabetic(10))
                .phone(RandomStringUtils.randomNumeric(10))
                .address(RandomStringUtils.randomAlphanumeric(20))
                .build();

        Address address = Address.builder()
                .id(UUID.randomUUID().toString())
                .city("Medellin")
                .countryISOCode("CO")
                .regionISOCode("ANT")
                .description("home")
                .name("home")
                .phoneNumber("0344445878")
                .street1("cra 80 # 33 - 50")
                .street2("apto. 505")
                .zip("50032")
                .build();

        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Product product = Product.builder()
                    .id(UUID.randomUUID().toString())
                    .name(RandomStringUtils.randomAlphabetic(10))
                    .description(RandomStringUtils.randomAlphabetic(20))
                    .build();
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(RandomUtils.nextInt(1, 3));
            orderItem.setFinalPrice(RandomUtils.nextFloat(5000, 10000));
            items.add(orderItem);
        }

        return Order.builder()
                .id(UUID.randomUUID().toString())
                .items(items)
                .status(Order.Status.CREATED)
                .storeId(store.getId())
                .shippingAddressId(address.getId())
                .userId(user.getId())
                .build();
    }
}
