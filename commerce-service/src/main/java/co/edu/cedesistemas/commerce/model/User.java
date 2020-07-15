package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(of = "id", callSuper=true)
@Document("user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends RepresentationModel<Store> {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
}
