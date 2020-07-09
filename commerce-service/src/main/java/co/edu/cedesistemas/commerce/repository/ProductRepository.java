package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByNameLike(String name);
}
