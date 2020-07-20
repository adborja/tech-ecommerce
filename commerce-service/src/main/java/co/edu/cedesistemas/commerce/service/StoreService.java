package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.repository.StoreRepository;
import co.edu.cedesistemas.commerce.service.interfaces.IStoreService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile("!" + SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class StoreService implements IStoreService {
    private final StoreRepository repository;

    @Override
    public Store createStore(final Store store) {
        store.setId(UUID.randomUUID().toString());
        store.setCreatedAt(LocalDateTime.now());
        return repository.save(store);
    }

    @Override
    public Store getById(final String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Store> getByType(final Store.Type type) {
        return repository.findByType(type);
    }

    @Override
    public List<Store> getByName(final String name) {
        return repository.findByNameLike(name);
    }

    @Override
    public Store updateStore(String id, Store store) {
        Store found = getById(id);
        if (found == null) return null;
        BeanUtils.copyProperties(store, found, Utils.getNullPropertyNames(store));
        return repository.save(found);
    }
}
