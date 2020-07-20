package co.edu.cedesistemas.commerce.social.repository;

import co.edu.cedesistemas.commerce.social.model.Product;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends Neo4jRepository<Product, String> {
    @Query("MATCH (p:Product)<-[:LIKES]-(user:User {id:$userId}) RETURN p")
    Set<Product> findByUserLiked(@Param("userId") String userId);

    Set<Product> findAllById(Iterable<String> productIds);

}
