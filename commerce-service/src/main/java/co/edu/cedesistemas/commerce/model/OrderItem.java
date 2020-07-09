package co.edu.cedesistemas.commerce.model;

import lombok.Data;

@Data
public class OrderItem {
    private Product product;
    private Float finalPrice;
    private Integer quantity;

    public Product getProduct() {return product;}
    public Float getFinalPrice() {return finalPrice;}
    public void setProduct(Product prodParam){product = prodParam;}
    public void setQuantity(Integer quantityParam) {quantity=quantityParam;}
    public void setFinalPrice(Float finalPriceParam) {finalPrice=finalPriceParam;}
}
