package co.edu.cedesistemas.commerce.shipping.model;

public class Cancel {

    private reason id;
    private String  description;

    public enum reason {
                ADDRESS_NOT_FOUND,
                CUSTOMER_NOT_FOUND,
                CUSTOMER_REJECTED
    }
}
