package co.edu.cedesistemas.commerce.service;

import co.edu.cedesistemas.commerce.model.Store;

import java.util.List;

public interface IUserService {

    Store createStore(Store store);

    Store getById(String id);

    List<Store> getByType(Store.Type type);

    List<Store> getByName(String name);

    Store updateStore(String id, Store store) throws Exception;
}
