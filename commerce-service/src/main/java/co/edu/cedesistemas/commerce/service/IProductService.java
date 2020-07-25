package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(String id);
    Product createProduct(Product product);
    Product deleteProductById(String id);
    List<Product> getProductByName (String name);
    Product updateProductById (String id, Product product);

    }