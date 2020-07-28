package co.edu.cedesistemas.commerce.loyalty.repository;

import co.edu.cedesistemas.commerce.loyalty.model.UserOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends MongoRepository<UserOrder, String> {
}
