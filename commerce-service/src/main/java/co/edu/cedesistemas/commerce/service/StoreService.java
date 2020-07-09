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
    	Optional<Store> store = repository.findById(id);
    	return store.isPresent() ? store.get() : null;
    }

    public List<Store> getByType(final Store.Type type) throws Exception {
    	
    	List<Store> types = repository.findByType(type);
    	
    	if (types.isEmpty())
    		throw new Exception("No se encontraron tiendas para el tipo seleccionado.");
    	
        return types;
    }

    public List<Store> getByName(final String name) throws Exception {
    	List<Store> names = repository.findByNameLike(name);
    	
    	if (names.isEmpty())
    		throw new Exception("No se encontraron tiendas con el nombre definido.");
    	
        return names;
    }

    public Store updateStore(String id, Store store) {
    	return repository.findById(id).isEmpty() ? null : 
    		repository.save(store);
    }
}
