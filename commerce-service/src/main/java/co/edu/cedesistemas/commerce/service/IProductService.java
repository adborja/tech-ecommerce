package co.edu.cedesistemas.commerce.service;

import java.util.List;

import co.edu.cedesistemas.commerce.model.Product;

public interface IProductService {

	Product createProduct(Product product);
	void deleteProduct(String id);
	Product getById(String id);
	List<Product> getByName(String name) throws Exception;
	Product updateProduct(String id, Product product) throws Exception;
}
