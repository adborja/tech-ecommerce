package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;

public interface IProductService {
    Product createProduct(final Product product);
    Product getById(final String id);
    List<Product> getByName(final String name);
    Product updateProduct(String id, Product product) throws Exception;
    void deleteProduct(String id);
}
