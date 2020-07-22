package co.edu.cedesistemas.commerce.registration.repository;

import co.edu.cedesistemas.commerce.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
