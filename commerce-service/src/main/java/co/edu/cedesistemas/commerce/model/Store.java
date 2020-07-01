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
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setId(String id) {
		this.id = id;
	}

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
