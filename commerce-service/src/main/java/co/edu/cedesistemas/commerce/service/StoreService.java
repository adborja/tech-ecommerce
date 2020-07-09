package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class StoreService {
    private final StoreRepository repository;

    public Store createStore(final Store store) {
        store.setId(UUID.randomUUID().toString());
        store.setCreatedAt(LocalDateTime.now());
        return repository.save(store);
    }

    public Store getById(final String id) {
        return repository.findById(id)
                .orElse(null);
    }

    public List<Store> getByType(final Store.Type type) {
        return repository.findByType(type);
    }

    public List<Store> getByName(final String name) {
        return repository.findByNameLike(name);
    }

    public Store updateStore(String id, Store store) {
        return repository.findById(id)
                .map(actualStore -> updateStore(actualStore, store))
                .map(repository::save)
                .orElse(null);
    }

    private Store updateStore(Store actualStore, Store newStore){
        actualStore.setPhone(newStore.getPhone());
        actualStore.setAddress(newStore.getAddress());

        return actualStore;
    }
}
