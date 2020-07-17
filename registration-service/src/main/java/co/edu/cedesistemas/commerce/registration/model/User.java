package co.edu.cedesistemas.commerce.registration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@ToString
@Document
public class User extends RepresentationModel<User> {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String address;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;

    public enum Status {
        ACTIVE,
        INACTIVE,
        BLOCKED
    }
}
