package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StoreRepository extends MongoRepository<Store, String> {
    List<Store> findByNameLike(String name);

    List<Store> findByType(Store.Type type);

    List<Store> findByName(String name);
}
