package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "id")
@ToString
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

    public String getId() {return id;}
    public void setId(String IdVal) {id = IdVal;}
    public void setCreatedAt(LocalDateTime valcreated) { createdAt=valcreated;}
    public void setUpdateAt(LocalDateTime valcreated) { updatedAt=valcreated;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Type getType() {
        return type;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
