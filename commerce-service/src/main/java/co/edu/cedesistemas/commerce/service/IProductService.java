package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Product createProduct(Product product);

    List<Product> getProductByNamePrefix(String name);

    Optional<Product> updateProduct(String id, Product product) throws Exception;

    boolean deleteProduct(String id);

    Optional<Product> findById(String id);
}
