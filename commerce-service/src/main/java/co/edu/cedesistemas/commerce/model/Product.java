package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = "id")
@Document("product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;

    public String getId() {return id;}
    public void setId(String idParam){ id = idParam;}
    public String getName() {return name;}
    public void setDescription(String descParam) {description=descParam;}
    public void setName(String nameParam) {name=nameParam;}
    public String getDescription() {return description;}

}
