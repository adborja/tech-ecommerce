package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id")
@Document("user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends RepresentationModel<User> implements Serializable {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
}
