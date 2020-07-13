package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "id")
@Document("product")
public class Product extends RepresentationModel<Product> {
    @Id
    private String id;
    private String name;
    private String description;
}
