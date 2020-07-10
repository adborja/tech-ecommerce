package co.edu.cedesistemas.commerce.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = "id")
@Document("address")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
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
