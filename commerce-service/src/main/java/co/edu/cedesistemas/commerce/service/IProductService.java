package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Product;

import java.util.List;

public interface IProductService {
    public Product createProduct(final Product product);
    public Product getById(final String id);
    public List<Product> getByName(final String name);
    public void deleteProductById (final String id);
    public Product updateProduct (final String id, final Product product);
}
