package co.edu.cedesistemas.gateway.auth.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private Integer active = 1;
    private boolean locked;
    private boolean expired;
    private boolean enabled = true;
    private Set<Role> role;
}
