package co.edu.cedesistemas.commerce.loyalty.repository;

import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoreRepository extends MongoRepository<UserStore, String> {
}
