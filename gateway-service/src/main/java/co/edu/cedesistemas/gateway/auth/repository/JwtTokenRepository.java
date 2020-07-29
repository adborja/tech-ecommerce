package co.edu.cedesistemas.gateway.auth.repository;

import co.edu.cedesistemas.gateway.auth.model.JwtToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtTokenRepository extends MongoRepository<JwtToken, String> {
}
