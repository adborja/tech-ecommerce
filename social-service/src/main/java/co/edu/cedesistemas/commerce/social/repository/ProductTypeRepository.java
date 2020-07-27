package co.edu.cedesistemas.commerce.social.repository;

import co.edu.cedesistemas.commerce.social.model.ProductType;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends Neo4jRepository<ProductType, Long> {
    Optional<ProductType> findByName(String name);
}
