package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(of = "id", callSuper = true)
@ToString
@Document
public class Address extends RepresentationModel<Address> {
    @Id
    private String id;
    private String name;
    private String description;
    private String city;
    private String countryISOCode;
    private String regionISOCode;
    private String phoneNumber;
    private String street1;
    private String street2;
    private String street3;
    private String zip;
}
