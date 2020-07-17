package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;

import java.util.List;

public interface IStoreService {
    Store createStore(final Store store);
    Store getById(final String id);
    List<Store> getByType(final Store.Type type);
    List<Store> getByName(final String name);
    Store updateStore(String id, Store store) throws Exception;
}
