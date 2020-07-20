package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product save(Product product);
    Product getById(String id);
    List<Product> getAllByNameLike(String name);
    void deleteById(String id);
}
