package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@XSlf4j
@Service
//@AllArgsConstructor
public class StoreService {
    private final StoreRepository repository;
    
    public StoreService (StoreRepository repository){
    	this.repository = repository;
    }

    public Store createStore(final Store store) {
        store.setId(UUID.randomUUID().toString());
        store.setCreatedAt(LocalDateTime.now());
        return repository.save(store);
            }

    public Store getById(final String id) {
        Optional<Store>  storeId = repository.findById(id);
        if (storeId.isPresent()) {
            return storeId.get();
        }
        else {return null;}

    }

    public List<Store> getByType(final Store.Type type) {
        List<Store>  storeType =   repository.findByType(type);
        return storeType;
    }

    public List<Store> getByName(final String name) {
        List<Store>  storeName =   repository.findByNameLike(name);
        return storeName;
    }

    public Store updateStore(String id, Store store)
    {
        Optional<Store> storeUpdate = repository.findById(id);
        if (storeUpdate.isPresent())
        {
            //storeUpdate.setUpdateAt(LocalDateTime.now() );
            return repository.save(store);
        }
        else {
            return null;

        }

    }
}
