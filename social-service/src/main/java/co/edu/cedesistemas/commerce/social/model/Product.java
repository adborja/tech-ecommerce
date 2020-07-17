package co.edu.cedesistemas.commerce.social.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@NodeEntity
public class Product extends RepresentationModel<Product> {
    @Id
    private String id;
}
