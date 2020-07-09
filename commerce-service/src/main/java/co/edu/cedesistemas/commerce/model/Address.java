package co.edu.cedesistemas.commerce.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = "id")
@Document("address")
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

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryISOCode() {
        return countryISOCode;
    }

    public String getDescription() {
        return description;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRegionISOCode() {
        return regionISOCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet1() {
        return street1;
    }

    public void setCountryISOCode(String countryISOCode) {
        this.countryISOCode = countryISOCode;
    }

    public String getStreet2() {
        return street2;
    }

    public String getStreet3() {
        return street3;
    }

    public String getZip() {
        return zip;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegionISOCode(String regionISOCode) {
        this.regionISOCode = regionISOCode;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    public String getName() {return name;}
    public String getCity() {return city;}
}
