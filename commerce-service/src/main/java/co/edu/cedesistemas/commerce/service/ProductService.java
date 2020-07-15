package co.edu.cedesistemas.commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{

	private final ProductRepository repository;
	@Override
	public List<Product> getByName(final String name) throws Exception{
		List<Product> products = repository.findByNameLike(name);
		
		if(products.isEmpty()) 
			throw new Exception("No se encontraron productos");
		
		return products;
	}
	@Override
	public Product createProduct(final Product product) {
		return repository.save(product);
	}
	@Override
	public Product getById(final String id) {
		Optional<Product> product = repository.findById(id);
		return product.isPresent() ? product.get() : null;
	}
	@Override
	public Product updateProduct(final String id, final Product product) {
		return repository.findById(id).isEmpty() ? null : 
    		repository.save(product);
	}
	@Override
	public void deleteProduct(final String id) {
		repository.deleteById(id);
	}
	
}
