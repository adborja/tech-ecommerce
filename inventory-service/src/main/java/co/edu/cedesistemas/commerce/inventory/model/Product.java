package co.edu.cedesistemas.commerce.inventory.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@Document
public class Product {
    @Id
    private String id;
    private String storeId;
    private String catalogId;
    private String name;
    private String description;
    private List<SKU> skus;
    private boolean active;
    private Set<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
