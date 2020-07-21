package co.edu.cedesistemas.commerce.registration.repository;


import co.edu.cedesistemas.commerce.registration.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByEmail(String email);

}
