package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.model.Store;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Product createProduct(Product product);

    Product updateProduct(Product store);

    void deleteProduct(Product product);

    Optional<Product> getProduct(String id);

    List<Product> getProductbyName(String name);




}
