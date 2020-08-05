package co.edu.cedesistemas.commerce.shipping.model;

import lombok.Data;

@Data
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
