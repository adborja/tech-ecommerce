package co.edu.cedesistemas.commerce.cart.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Document
public class Cart {
    @Id
    private String id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private Float total;
    private String currency;
    private Set<CartItem> items;

    public void addItem(CartItem item) {
        if (items == null) {
            items = new HashSet<>();
        }
        items.add(item);
    }

    public void removeItem(CartItem item) {
        if (items == null) {
            items = new HashSet<>();
        }
        items.remove(item);
    }

    @Data
    @EqualsAndHashCode(of = "id")
    public static class CartItem {
        private String id;
        private String name;
        private Integer quantity;
        private Float price;
        private String currency;
    }
}
