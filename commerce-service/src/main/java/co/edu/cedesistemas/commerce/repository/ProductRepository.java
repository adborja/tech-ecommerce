package co.edu.cedesistemas.commerce.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.cedesistemas.commerce.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByNameLike(String name);
}
