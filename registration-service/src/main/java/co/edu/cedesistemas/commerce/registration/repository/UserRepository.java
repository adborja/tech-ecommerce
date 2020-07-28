package co.edu.cedesistemas.commerce.registration.repository;

import co.edu.cedesistemas.commerce.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
