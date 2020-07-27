package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(Product product);

    Product getById(String id);

    void deleteProduct(String id);

    List<Product> getByName(String name);

    Product updateProduct(String id, Product product);
}
