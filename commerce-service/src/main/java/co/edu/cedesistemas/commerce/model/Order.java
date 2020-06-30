package co.edu.cedesistemas.commerce.model;

import co.edu.cedesistemas.common.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class Order implements Entity<String> {
    private String id;
    private User user;
    private Store store;
    private Address shippingAddress;

    @Override
    public String getId() {
        return id;
    }
}