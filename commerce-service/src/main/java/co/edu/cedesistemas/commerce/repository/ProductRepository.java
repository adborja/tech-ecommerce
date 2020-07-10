package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);

    List<Product> findByNameLike(String name);
}
