package co.edu.cedesistemas.commerce.social.repository;

import co.edu.cedesistemas.commerce.social.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (u:User)-[:IS_FRIEND_OF]-(user:User {id:$userId}) RETURN u")
    Set<User> findFriendsByUser(@Param("userId") String userId);
}
