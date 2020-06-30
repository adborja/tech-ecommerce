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

    public List<Store> getByType(final Store.Type type) {
        return repository.findByType(type);
    }
}
