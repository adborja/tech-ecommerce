package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        Optional<Store> foundStore = this.repository.findById(id);

        if (foundStore.isPresent()) {
            return foundStore.get();
        }
        return null;
    }

    public List<Store> getByType(final Store.Type type) {
        return this.repository.findByType(type);
    }

    public List<Store> getByName(final String name) {
        return this.repository.findByName(name);
    }

    public Store updateStore(String id, Store store) {
        Optional<Store> foundStore = this.repository.findById(id);

        if (foundStore.isPresent()) {
            Store storeToUpdate = foundStore.get();
            storeToUpdate.setPhone(store.getPhone());
            storeToUpdate.setAddress(store.getAddress());
            return this.repository.save(storeToUpdate);
        }
        return null;
    }
}
