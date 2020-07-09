package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(of = "id")
@Document("product")
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
}
