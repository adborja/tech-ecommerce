package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

}
