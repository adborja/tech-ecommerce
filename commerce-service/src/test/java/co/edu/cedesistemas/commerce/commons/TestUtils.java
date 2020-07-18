package co.edu.cedesistemas.commerce.commons;

import co.edu.cedesistemas.commerce.model.Address;
import co.edu.cedesistemas.commerce.model.Order;
import co.edu.cedesistemas.commerce.model.OrderItem;
import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public final class TestUtils {
    public static Product buildProduct(final String name, final String description) {
        Product p = new Product();
        p.setId(UUID.randomUUID().toString());
        p.setName(name);
        p.setDescription(description);
        return p;
    }

    public static Order buildOrder(Store store, User user, Address shippingAddress) {
        final Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setShippingAddressId(shippingAddress.getId());
        order.setStatus(Order.Status.DELIVERED);
        order.setStoreId(store.getId());
        order.setUserId(user.getId());
        return order;
    }

    public static Address buildAddress() {
        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setCity("Medellin");
        address.setCountryISOCode("CO");
        address.setRegionISOCode("ANT");
        address.setDescription("home");
        address.setName("home");
        address.setPhoneNumber("0344445878");
        address.setStreet1("cra 80 # 33 - 50");
        address.setStreet2("apto. 505");
        address.setZip("50032");
        return address;
    }

    public static User buildUser(final String email, final String name, final String lastName) {
        final User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setName(name);
        user.setLastName(lastName);
        return user;
    }

    public static Store buildStore(final String name, final String phone, final String address, final Store.Type type) {
        final Store store = new Store();
        store.setId(UUID.randomUUID().toString());
        store.setName(name);
        store.setPhone(phone);
        store.setAddress(address);
        store.setType(type);
        store.setCreatedAt(LocalDateTime.now());
        return store;
    }

    public static OrderItem buildOrderItem(final Product product, final Integer quantity, Float finalPrice) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setProductId(product.getId());
        orderItem.setFinalPrice(finalPrice);
        orderItem.setQuantity(quantity);
        return orderItem;
    }
}
