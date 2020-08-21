package co.edu.cedesistemas.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String id;
    private String city;
    private String country;
    private String state;
    private String street1;
    private String street2;
    private String street3;
    private String zip;
}
