package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@Document("order")
public class Order {
    @Id
    private String id;
    private String userId;
    private String storeId;
    private String shippingAddressId;
    private Status status;
    private LocalDateTime createdAt;
    private List<OrderItem> items;

    public enum Status {
        CREATED,
        ACCEPTED,
        CONFIRMED,
        CANCELLED,
        SHIPPED,
        DELIVERED
    }

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {return user;}

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {return store;}

    public void setStore(Store store) {
        this.store = store;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createAtParam) { createdAt=createAtParam;}

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}