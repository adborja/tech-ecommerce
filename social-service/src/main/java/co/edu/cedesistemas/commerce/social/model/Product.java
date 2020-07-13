package co.edu.cedesistemas.commerce.social.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Id;

@Data
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    private String id;
}
