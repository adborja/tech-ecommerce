package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface StoreRepository extends MongoRepository<Store, String> {
    List<Store> findByNameLike(String name);
    List<Store> findByType(Store.Type type);
   // Optional<Store>  findById(String id);
    //<S extends Store> S  save(S store);


}
