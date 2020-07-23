package co.edu.cedesistemas.commerce.social.repository;

import co.edu.cedesistemas.commerce.social.model.Location;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends Neo4jRepository<Location, Long> {
    Optional<Location> findByName(String name);
}
