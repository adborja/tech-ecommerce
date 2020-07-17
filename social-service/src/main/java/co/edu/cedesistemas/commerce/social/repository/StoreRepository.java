package co.edu.cedesistemas.commerce.social.repository;

import co.edu.cedesistemas.commerce.social.model.Store;
import lombok.Data;
import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface StoreRepository extends Neo4jRepository<Store, String> {
    Set<Store> findByUserLiked(@Param("userId") String userId);

    List<StoreOccurrence> findRecommendationByProducts(@Param("userId") String userId, @Param("zone") String zone,
                                                       @Param("productType") String productType,
                                                       @Param("limit") Integer limit);

    List<StoreOccurrence> findRecommendationByStores(@Param("userId") String userId, @Param("zone") String zone,
                                                     @Param("productType") String productType,
                                                     @Param("limit") Integer limit);

    List<StoreOccurrence> findRecommendationByStores(@Param("userId") String userId,
                                                     @Param("zone") String zone, @Param("limit") Integer limit);

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
