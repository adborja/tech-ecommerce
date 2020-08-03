package co.edu.cedesistemas.commerce.loyalty.repository;

import co.edu.cedesistemas.commerce.loyalty.model.UserStore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStoreRepository extends MongoRepository<UserStore, String> {
    Optional<UserStore> findByStoreIdAndUserId(String storeId, String userId);
    List<UserStore> findByStoreId(String storeId);
}
