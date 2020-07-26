package co.edu.cedesistemas.commerce.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import co.edu.cedesistemas.commerce.model.Product;
import co.edu.cedesistemas.commerce.repository.ProductRepository;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
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
		return repository.findById(id).orElse(null);
	}
	@Override
	public Product updateProduct(final String id, final Product product) {
		Product found = getById(id);
		if(found == null) {
			log.warn("Product not found {}",id);
			return null;
		}
				
		BeanUtils.copyProperties(product, found, Utils.getNullPropertyNames(product));
		
		return repository.save(found); 
	}
	@Override
	public void deleteProduct(final String id) {
		repository.deleteById(id);
	}
	
}
