package co.edu.cedesistemas.commerce.social.repository;

import co.edu.cedesistemas.commerce.social.model.Store;
import lombok.Data;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StoreRepository extends Neo4jRepository<Store, String> {
	
	@Query("MATCH (store:Store)<-[:LIKES]-(:User {id:$userId}) RETURN store")
    Set<Store> findByUserLiked(@Param("userId") String userId);

    @Query("MATCH (user:User {id: $userId})-[:IS_FRIEND_OF]->(friend)-[:LIKES]->(product:Product)<-[:HAS]-(store:Store)-[:LOCATED_IN]->"
    		+ "(loc:Location {zone: $zone}), (store)-[:SERVES]->(type:ProductType {name: $productType}) RETURN store.id as storeId, count(*) AS occurrence ORDER BY occurrence DESC LIMIT $limit")  
    List<StoreOccurrence> findRecommendationByProducts(@Param("userId") String userId, @Param("zone") String zone,
                                                       @Param("productType") String productType,
                                                       @Param("limit") Integer limit);

    @Query("MATCH (user:User {id: $userId})-[:IS_FRIEND_OF]->(friend)-[:LIKES]->(store:Store)-[:LOCATED_IN]->(loc:Location {zone:$zone}),"
    		+ "(store)-[:SERVES]->(p {name:$productType}) RETURN store.id as storeId, count(*) AS ocurrence ORDER BY ocurrence DESC LIMIT $limit")
    List<StoreOccurrence> findRecommendationByStores(@Param("userId") String userId, @Param("zone") String zone,
                                                     @Param("productType") String productType,
                                                     @Param("limit") Integer limit);

    @Query("MATCH (user:User {id: $userId})-[:IS_FRIEND_OF]->(friend)-[:LIKES]->(store:Store)-[:LOCATED_IN]->(loc:Location {zone:$zone}) "
    		+ "RETURN store.id AS storeId, count(*) AS ocurrence ORDER BY ocurrence DESC LIMIT $limit") 
    List<StoreOccurrence> findRecommendationByStores(@Param("userId") String userId,
                                                     @Param("zone") String zone, @Param("limit") Integer limit);

    
    @Query("MATCH (:User)-[:PURCHASES]->(p:Product)<-[:SERVES]-(:Store {id:$storeId}) "
    		+ "RETURN p.id AS productId, count(*) AS ocurrence ORDER BY ocurrence DESC LIMIT $limit")
    List<ProductOccurrence> findTopNProducts(@Param("storeId") String storeId, @Param("limit") Integer limit);


    @QueryResult
    @Data
    class StoreOccurrence {
        private String storeId;
        private Integer occurrence;
        private String zone;
    }

    @QueryResult
    @Data
    class ProductOccurrence {
        private String productId;
        private Integer occurrence;
    }
}
