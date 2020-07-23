package co.edu.cedesistemas.commerce.social.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity(label = "Location")
public class Location {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String country;
    private String city;
    private String zone;
}
