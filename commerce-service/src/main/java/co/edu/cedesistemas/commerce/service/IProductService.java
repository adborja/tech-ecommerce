package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Product createProduct(Product product);

    Product getById(String id);

    List<Product> getByName(final String name);

    void deleteProduct(String id);

    Product updateProduct(String id, Product product) throws Exception;

}
