package co.edu.cedesistemas.commerce.model;

import co.edu.cedesistemas.common.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
@ToString
public class Store implements Entity<String> {
    private String id;
    private String name;
    private String phone;
    private String address;
    private Type type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String getId() {
        return id;
    }

    public enum Type {
        TECHNOLOGY,
        SPORTS,
        PETS,
        BOOKS,
        FITNESS,
        AUTO_PARTS,
        FOOD
    }
}
