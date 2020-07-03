package co.edu.cedesistemas.commerce.repository;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.common.repository.Repository;

import java.util.List;

public interface StoreRepository extends Repository<Store, String> {
    List<Store> findByName(String name);
    List<Store> findByType(Store.Type type);
}
