package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.repository.StoreRepository;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Profile("!"+ SpringProfile.SANDBOX)
@Service
@AllArgsConstructor
public class StoreService implements IStoreService{
    private final StoreRepository repository;

    public Store createStore(final Store store) {
        store.setId(UUID.randomUUID().toString());
        store.setCreatedAt(LocalDateTime.now());
        return repository.save(store);
    }

    public Store getById(final String id) {
        return repository.findById(id).get();
    }

    public List<Store> getByType(final Store.Type type) {
        return repository.findByType(type);
    }

    public List<Store> getByName(final String name) {
        return repository.findByNameLike(name);
    }

    public Store updateStore(String id, Store store) {
        Store storeFind = repository.findById(id).get();
        BeanUtils.copyProperties(store,storeFind, Utils.getNullPropertyNames(store));
        return repository.save(storeFind);
    }
}
