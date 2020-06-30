package co.edu.cedesistemas.commerce.model;

import co.edu.cedesistemas.common.model.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
public class Address implements Entity<String> {
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

    @Override
    public String getId() {
        return id;
    }
}
