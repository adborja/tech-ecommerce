package co.edu.cedesistemas.commerce.service.interfaces;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;

public interface IProductService {
    Product getById(final String id);
    List<Product> getProductsByName(String name);
    Product updateProductById(String id);
    Product deleteProductById(String id);
    Product createProduct(Product product);
}
