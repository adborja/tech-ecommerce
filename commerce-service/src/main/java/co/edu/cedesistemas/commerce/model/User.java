package co.edu.cedesistemas.commerce.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = "id")
@Document("user")
public class User {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String email;
}
