package co.edu.cedesistemas.commerce.inventory.repository;

import co.edu.cedesistemas.commerce.inventory.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

}
