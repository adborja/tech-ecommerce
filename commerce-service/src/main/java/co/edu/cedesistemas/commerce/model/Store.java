package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode(of = "id")
@ToString
@Data
@Document
public class Store {
    @Id
    private String id;
    private String name;
    private String phone;
    private String address;
    private Type type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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