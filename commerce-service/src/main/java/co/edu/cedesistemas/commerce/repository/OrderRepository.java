package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface OrderRepository extends MongoRepository<Order, String> {
}
