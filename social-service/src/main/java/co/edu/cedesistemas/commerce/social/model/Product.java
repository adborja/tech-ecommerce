package co.edu.cedesistemas.commerce.social.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@EqualsAndHashCode(of = "id")
@NodeEntity
public class Product {
    @Id
    private String id;
}
