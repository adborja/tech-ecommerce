package co.edu.cedesistemas.commerce.shipping.model;

import lombok.Data;

@Data
public class CanceledDelivery {

    private Reason id;
    private String  description;

    public enum Reason {
        ADDRESS_NOT_FOUND,
        CUSTOMER_NOT_FOUND,
        CUSTOMER_REJECTED
    }
}