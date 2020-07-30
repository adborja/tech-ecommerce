package co.edu.cedesistemas.commerce.inventory.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SKU {
    private String id;
    private String sku;
    private boolean purchasable;
    private float price;
    private float promoPrice;
    private LocalDate displayDate;
    private LocalDate expirationDate;
    private String gtin;
    private String manufacturerPartNumber;
}
