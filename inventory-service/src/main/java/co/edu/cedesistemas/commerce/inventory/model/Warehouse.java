package co.edu.cedesistemas.commerce.inventory.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Document
public class Warehouse {
    @Id
    private String id;
    private boolean active;
    private String city;
    private String countryISOCode;
    private String description;
    private Double latitude;
    private Double longitude;
    private String name;
    private String regionISOCode;
    private String street;
    private String zip;
    private Set<Item> items;

    @EqualsAndHashCode(of = "id")
    @Data
    public static class Item {
        private String id;
        private LocalDate modifiedDate;
        private int quantity;
        private int reservedQuantity;
        private String sku;
    }
}
