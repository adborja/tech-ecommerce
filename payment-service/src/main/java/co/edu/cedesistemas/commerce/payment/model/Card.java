package co.edu.cedesistemas.commerce.payment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Card {
    @Id
    private String id;
    private String name;
    private Address address;
    private Brand brand;
    private String country;
    private String userId;
    private Integer expirationMonth;
    private Integer expirationYear;
    private String fingerprint;
    private String lastFour;

    @Data
    public static class Address {
        private String city;
        private String country;
        private String line1;
        private String line2;
        private String state;
        private String zip;
    }

    public enum Brand {
        VISA,
        MASTERCARD
    }

    public enum Funding {
        CREDIT,
        DEBIT,
        PREPAID,
        UNKNOWN
    }
}
