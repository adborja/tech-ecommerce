package co.edu.cedesistemas.commerce.service.sandbox;

import co.edu.cedesistemas.commerce.model.Store;
import co.edu.cedesistemas.commerce.service.interfaces.IStoreService;
import co.edu.cedesistemas.common.SpringProfile;
import co.edu.cedesistemas.common.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Profile(SpringProfile.SANDBOX)
@Service
public class StoreServiceSandbox implements IStoreService {
    @Override
    public Store createStore(Store store) {
        store.setId(UUID.randomUUID().toString());
        store.setCreatedAt(LocalDateTime.now());
        return store;
    }

    @Override
    public Store getById(String id) {
        Store store = new Store();
        store.setId(id);
        store.setCreatedAt(LocalDateTime.now());
        store.setType(Store.Type.TECHNOLOGY);
        store.setPhone("+5744444444");
        store.setName("test store");
        store.setAddress("123 Fake Street.");
        return store;
    }

    @Override
    public List<Store> getByType(Store.Type type) {
        Store store1 = new Store();
        store1.setId(UUID.randomUUID().toString());
        store1.setCreatedAt(LocalDateTime.now());
        store1.setType(type);
        store1.setPhone("+5744444444");
        store1.setName("test store");
        store1.setAddress("123 Fake Street.");

        Store store2 = new Store();
        store2.setId(UUID.randomUUID().toString());
        store2.setCreatedAt(LocalDateTime.now());
        store2.setType(type);
        store2.setPhone("+5744444445");
        store2.setName("test store 2");
        store2.setAddress("123 Fake Street.");

        return Arrays.asList(store1, store2);
    }

    @Override
    public List<Store> getByName(String name) {
        Store store1 = new Store();
        store1.setId(UUID.randomUUID().toString());
        store1.setCreatedAt(LocalDateTime.now());
        store1.setType(Store.Type.TECHNOLOGY);
        store1.setPhone("+5744444444");
        store1.setName("test store");
        store1.setAddress("123 Fake Street.");

        Store store2 = new Store();
        store2.setId(UUID.randomUUID().toString());
        store2.setCreatedAt(LocalDateTime.now());
        store2.setType(Store.Type.BOOKS);
        store2.setPhone("+5744444445");
        store2.setName("test store 2");
        store2.setAddress("123 Fake Street.");

        return Arrays.asList(store1, store2);
    }

    @Override
    public Store updateStore(String id, Store store) throws Exception {
        if (store.getId() != null) {
            throw new Exception("id cannot be updated");
        }
        Store found = getById(id);
        BeanUtils.copyProperties(store, found, Utils.getNullPropertyNames(store));
        return found;
    }
}
