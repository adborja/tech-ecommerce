package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@Document("user")
public class User extends RepresentationModel<User> {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
}
