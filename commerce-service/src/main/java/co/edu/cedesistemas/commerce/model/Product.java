package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(of = "id")
@ToString
@Document("product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends RepresentationModel<Product> {
    @Id
    private String id;
    private String name;
    @NotNull
    private String description;
}
