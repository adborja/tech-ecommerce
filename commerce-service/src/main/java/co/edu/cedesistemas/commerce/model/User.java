package co.edu.cedesistemas.commerce.model;

import co.edu.cedesistemas.common.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class User implements Entity<String> {
    private String id;
    private String name;
    private String lastName;
    private String email;

    @Override
    public String getId() {
        return id;
    }
}
