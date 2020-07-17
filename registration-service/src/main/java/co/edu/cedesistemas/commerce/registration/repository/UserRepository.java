package co.edu.cedesistemas.commerce.registration.repository;

import co.edu.cedesistemas.commerce.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    User save(User user);

    Optional<User> findUserById(String id);

}
